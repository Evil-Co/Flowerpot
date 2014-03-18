package com.evilco.flowerpot.proxy.metrics.mcstats;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.evilco.flowerpot.proxy.FlowerpotServer;
import com.evilco.flowerpot.proxy.metrics.IMetricsService;
import com.evilco.flowerpot.proxy.protocol.http.SimpleHttpClient;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MCStatsMetricsService implements IMetricsService {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (MCStatsMetricsService.class);

	/**
	 * Defines the report URL.
	 */
	public static final String REPORT_URL = "http://report.mcstats.org/plugin/%s";

	/**
	 * Defines the API revision.
	 */
	public static final int REVISION = 5;

	/**
	 * Indicates whether the next post will be an initial one.
	 */
	protected boolean firstPost = true;

	/**
	 * Stores the metrics gson instance.
	 */
	protected Gson gson = new Gson ();

	/**
	 * Stores all plugin details.
	 */
	protected Map<String, String> pluginDetailMap = new HashMap<> ();

	/**
	 * Stores the server branding.
	 */
	protected String serverBranding = null;

	/**
	 * Stores the server version.
	 */
	protected String serverVersion = null;

	/**
	 * Compresses a string with gzip.
	 * @param input
	 * @return
	 */
	public byte[] compress (String input) {
		// create streams
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ();
		GZIPOutputStream gzipOutputStream = null;

		// write data
		try {
			gzipOutputStream = new GZIPOutputStream (byteArrayOutputStream);
			gzipOutputStream.write (input.getBytes ("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				gzipOutputStream.close();
			} catch (Exception ignore) { }
		}

		// convert to byte array
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setServerDetail (String branding, String version) {
		this.serverBranding = branding;
		this.serverVersion = version;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPluginDetail (String name, String version) {
		this.pluginDetailMap.put (name, version);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postMetrics (UUID serverID, int playerCount) {
		this.postMetrics (serverID, playerCount, this.serverBranding, this.serverVersion);

		for (Map.Entry<String, String> pluginEntry : this.pluginDetailMap.entrySet ()) {
			this.postMetrics (serverID, playerCount, pluginEntry.getKey (), pluginEntry.getValue ());
		}

		// enable pings
		this.firstPost = false;
	}

	/**
	 * Posts metrics.
	 * @param name
	 * @param version
	 */
	public void postMetrics (UUID serverID, int playerCount, String name, String version) {
		// create data object
		MCStatsMetricsData metricsData = new MCStatsMetricsData (serverID, version, FlowerpotServer.VERSION, playerCount, !this.firstPost);


		// query server
		OutputStream outputStream = null;
		BufferedReader bufferedReader = null;

		try {
			// create URL
			String reportURL = String.format (REPORT_URL, URLEncoder.encode (name, SimpleHttpClient.CHARSET));

			// convert to URI
			URL url = new URL (reportURL);

			// open connection
			// TODO: Replace with netty post
			URLConnection urlConnection = url.openConnection ();

			// convert data
			String data = this.gson.toJson (metricsData);
			byte[] uncompressed = data.getBytes ();
			byte[] compressed = this.compress (data);

			// set properties
			urlConnection.addRequestProperty ("User-Agent", "MCStats/" + REVISION);
			urlConnection.addRequestProperty ("Content-Type", "application/json");
			urlConnection.addRequestProperty ("Content-Encoding", "gzip");
			urlConnection.addRequestProperty ("Content-Length", Integer.toString (compressed.length));
			urlConnection.addRequestProperty ("Accept", "application/json");
			urlConnection.addRequestProperty ("Connection", "close");

			// debug
			logger.debug ("Performing metrics post with %s bytes (%s uncompressed).", compressed.length, uncompressed.length);
			logger.trace ("Sending message: %s", data);

			// enable post
			urlConnection.setDoOutput (true);

			// write data
			outputStream = urlConnection.getOutputStream ();
			outputStream.write (compressed);
			outputStream.flush ();

			// read response
			bufferedReader = new BufferedReader (new InputStreamReader (urlConnection.getInputStream ()));
			String response = bufferedReader.readLine ();

			// debug
			logger.debug ("Received an answer from metrics service.");
			logger.trace ("Received answer: " + response);

			// check response
			if (response == null || response.startsWith("ERR") || response.startsWith("7")) {
				if (response == null)
					response = "null";
				else if (response.startsWith("7"))
					response = response.substring(response.startsWith("7,") ? 2 : 1);

				// log error
				logger.warn ("The metrics query did not succeed. Received response: %s", response);
			} else
				logger.debug ("Metrics query did succeed.");
		} catch (IOException ex) {
			logger.warn ("The metrics query did not succeed.", ex);
		} finally {
			IOUtils.closeQuietly (outputStream);
			IOUtils.closeQuietly (bufferedReader);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetPluginDetail () {
		this.pluginDetailMap.clear ();
	}
}
