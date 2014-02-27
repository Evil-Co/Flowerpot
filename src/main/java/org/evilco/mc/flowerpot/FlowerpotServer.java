package org.evilco.mc.flowerpot;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.configuration.IProxyConfiguration;
import org.evilco.mc.flowerpot.protocol.ServerListener;
import org.evilco.mc.flowerpot.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class FlowerpotServer {

	/**
	 * Stores the application logger.
	 */
	protected static final Logger logger = LogManager.getLogger (FlowerpotServer.class);

	/**
	 * Defines the server version.
	 */
	public static final String VERSION;

	/**
	 * Static Initialization
	 */
	static {
		String version = "(unknown)";
		Package p = FlowerpotServer.class.getPackage ();

		if (p != null && p.getImplementationVersion () != null) version = p.getImplementationVersion ();

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
		logger.info ("Flowerpot " + VERSION);
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
	public static final FlowerpotServer getInstance () {
		return instance;
	}

	/**
	 * Returns the application logger.
	 * @return
	 */
	public static final Logger getLogger () {
		return logger;
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
				return new MinecraftServer ("localhost", 25565);
			}

			@Override
			public List<ServerListener> getListenerList () {
				return new ArrayList<> (Arrays.asList (new ServerListener[] {

				}));
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
