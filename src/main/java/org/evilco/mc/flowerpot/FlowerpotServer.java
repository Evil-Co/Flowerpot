package org.evilco.mc.flowerpot;

import com.google.common.io.BaseEncoding;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.configuration.IProxyConfiguration;
import org.evilco.mc.flowerpot.protocol.ServerListener;
import org.evilco.mc.flowerpot.server.MinecraftServer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class FlowerpotServer {

	/**
	 * Stores the build ID.
	 */
	public static final String BUILD;

	/**
	 * Stores the application logger.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (FlowerpotServer.class);

	/**
	 * Defines the server version.
	 */
	public static final String VERSION;

	/**
	 * Static Initialization
	 */
	static {
		String version = "SNAPSHOT";
		String build = "dirty";
		Package p = FlowerpotServer.class.getPackage ();

		if (p != null) {
			if (p.getImplementationVersion () != null) version = p.getImplementationVersion ();
		}

		try {
			JarFile jar = new JarFile (FlowerpotServer.class.getProtectionDomain ().getCodeSource ().getLocation ().getFile ());

			Attributes attributes = jar.getManifest ().getMainAttributes ();
			build = attributes.getValue ("Implementation-Build");
		} catch (IOException ex) { }

		BUILD = build;
		VERSION = version;
	}

	/**
	 * Stores the current proxy configuration.
	 */
	protected IProxyConfiguration configuration;

	/**
	 * Stores the default server to connect to.
	 */
	protected MinecraftServer defaultServer;

	/**
	 * Stores the current proxy instance.
	 */
	protected static FlowerpotServer instance = null;

	/**
	 * Indicates whether the server is still alive.
	 */
	protected boolean isAlive = false;

	/**
	 * Stores the worker thread group.
	 */
	protected EventLoopGroup threadGroupWorker;

	/**
	 * Constructs a new FlowerpotServer.
	 * @param configuration
	 */
	protected FlowerpotServer (IProxyConfiguration configuration) {
		// log startup
		logger.info ("Flowerpot " + VERSION + " (git-" + BUILD + ")");
		logger.info ("Copyright (C) 2014 Evil-Co <http://www.evil-co.org>");
		logger.info ("---------------------------------------------------");


		// store arguments
		this.configuration = configuration;

		// create thread groups
		this.threadGroupWorker = new NioEventLoopGroup ();
	}

	/**
	 * Starts the server.
	 * @throws Exception
	 */
	public void bind () throws Exception {
		this.isAlive = true;

		// log
		logger.info ("Binding " + this.configuration.getListenerList ().size () + " listeners ...");

		// bind listeners
		for (ServerListener listener : this.configuration.getListenerList ()) {
			listener.bind (this.threadGroupWorker);
		}

		// enter server loop
		while (this.isAlive) {
			Thread.sleep (500);
		}
	}

	/**
	 * Returns the current configuration.
	 * @return
	 */
	public IProxyConfiguration getConfiguration () {
		return this.configuration;
	}

	/**
	 * Returns the server instance.
	 * @return
	 */
	public static FlowerpotServer getInstance () {
		return instance;
	}

	/**
	 * Returns the application logger.
	 * @return
	 */
	public static Logger getLogger () {
		return logger;
	}

	/**
	 * Returns an encoded server icon.
	 * @return
	 */
	public String getEncodedServerIcon () {
		return "data:image/png;base64," + BaseEncoding.base64 ().encode (this.configuration.getServerIcon ());
	}

	/**
	 * Main Entry Point
	 * @param arguments
	 */
	public static void main (String[] arguments) throws Exception {
		// TODO: Add proper implementation here
		instance = new FlowerpotServer (new IProxyConfiguration () {

			@Override
			public MinecraftServer getDefaultServer () {
				return new MinecraftServer ("localhost", 25570);
			}

			@Override
			public List<ServerListener> getListenerList () {
				return new ArrayList<> (Arrays.asList (new ServerListener[] {
					new ServerListener ("0.0.0.0", 25565)
				}));
			}

			@Override
			public byte[] getServerIcon () {
				try {
					return IOUtils.toByteArray (FlowerpotServer.class.getResourceAsStream ("/defaults/server-icon.png"));
				} catch (Exception ex) {
					return null;
				}
			}

			@Override
			public int getTimeout () {
				return 30000;
			}
		});

		// start instance
		instance.bind ();
	}
}
