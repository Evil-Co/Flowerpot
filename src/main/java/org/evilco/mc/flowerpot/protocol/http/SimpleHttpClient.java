package org.evilco.mc.flowerpot.protocol.http;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.evilco.mc.flowerpot.FlowerpotServer;

import javax.net.ssl.TrustManager;
import java.net.URI;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class SimpleHttpClient {

	/**
	 * Defines the requested charset.
	 */
	public static final String CHARSET = "UTF-8";

	/**
	 * Defines the connection timeout for queries.
	 */
	public static final int TIMEOUT = 6000;

	/**
	 * Defines the application user agent template.
	 */
	public static final String USER_AGENT_TEMPLATE = "Flowerpot/%s (%s)";

	public static void get (String url, EventLoop eventLoop, final HttpClientCallback callback, TrustManager trustManager) {
		Preconditions.checkNotNull (url, "URL cannot be null");
		Preconditions.checkNotNull (eventLoop, "EventLoop cannot be null");

		// parse URI
		final URI uri = URI.create (url);

		// verify URI
		Preconditions.checkNotNull (uri.getScheme (), "Missing URI scheme");
		Preconditions.checkNotNull (uri.getHost (), "Missing URI host");

		// check whether SSL is enabled
		boolean sslEnabled = uri.getScheme ().equalsIgnoreCase ("https");

		// verify scheme
		if (!uri.getScheme ().startsWith ("http")) throw new IllegalArgumentException ("Unsupported URI scheme: " + uri.getScheme ());

		// get correct port
		int port = uri.getPort ();
		if (port == -1) port = (sslEnabled ? 443 : 80);

		// create client instance
		Bootstrap httpClient = new Bootstrap ();

		// setup client
		httpClient.channel (NioSocketChannel.class);
		httpClient.group (eventLoop);
		httpClient.handler (new HttpChannelInitializer (callback, sslEnabled, trustManager));

		// set connection options
		httpClient.option (ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT);

		// create connection
		httpClient.remoteAddress (uri.getHost (), port);
		ChannelFuture future = httpClient.connect ();

		// append listener
		future.addListener (new ChannelFutureListener () {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void operationComplete (ChannelFuture future) throws Exception {
				// bail out on error
				if (!future.isSuccess ()) {
					if (callback != null) callback.error (-1);
					return;
				}

				// create query path
				String queryPath = uri.getRawPath () + (uri.getRawQuery () == null ? "" : "?" + uri.getRawQuery ());

				// create a new request object
				HttpRequest httpRequest = new DefaultHttpRequest (HttpVersion.HTTP_1_1, HttpMethod.GET, queryPath);

				// append HTTP headers
				httpRequest.headers ().add (HttpHeaders.Names.HOST, uri.getHost ());
				httpRequest.headers ().add (HttpHeaders.Names.ACCEPT_CHARSET, CHARSET);
				httpRequest.headers ().add (HttpHeaders.Names.USER_AGENT, String.format (USER_AGENT_TEMPLATE, FlowerpotServer.VERSION, FlowerpotServer.BUILD));

				// send finished request
				future.channel ().writeAndFlush (httpRequest);
			}
		});
	}
}
