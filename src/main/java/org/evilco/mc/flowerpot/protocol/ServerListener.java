package org.evilco.mc.flowerpot.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.FlowerpotServer;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftCodec;
import org.evilco.mc.flowerpot.protocol.codec.VarIntFrameCodec;

import java.util.concurrent.TimeUnit;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ServerListener {

	/**
	 * Stores the frame codec instance.
	 */
	public static final VarIntFrameCodec HANDLER_FRAME_CODEC = new VarIntFrameCodec ();

	/**
	 * Stores the minecraft codec instance.
	 */
	public static final MinecraftCodec HANDLER_MINECRAFT_CODEC = new MinecraftCodec ();

	/**
	 * Defines the client handler name.
	 */
	public static final String HANDLER_NAME_CLIENT = "client";

	/**
	 * Defines the encryption handler name.
	 */
	public static final String HANDLER_NAME_ENCRYPTION_CODEC = "encryptionCodec";

	/**
	 * Defines the frame decoder name.
	 */
	public static final String HANDLER_NAME_FRAME_CODEC = "frameCodec";

	/**
	 * Defines the Minecraft codec name.
	 */
	public static final String HANDLER_NAME_MINECRAFT = "minecraft";

	/**
	 * Defines the read timeout handler name.
	 */
	public static final String HANDLER_NAME_TIMEOUT = "timeout";

	/**
	 * Defines the listener attribute.
	 */
	public static final AttributeKey<ServerListener> LISTENER_ATTRIBUTE = AttributeKey.valueOf ("ServerListener");

	/**
	 * Stores the internal logger instance.
	 */
	protected static final Logger logger = LogManager.getLogger (ServerListener.class);

	/**
	 * Stores the connection channel.
	 */
	protected Channel channel = null;

	/**
	 * Stores the hostname to listen on.
	 */
	protected String hostname;

	/**
	 * Stores the port to listen on.
	 */
	protected int port;

	/**
	 * Indicates whether query is enabled on this listener.
	 */
	protected boolean isQueryEnabled;

	/**
	 * Indicates whether the proxy is enabled on this listener.
	 */
	protected boolean isProxyEnabled;

	/**
	 * Constructs a new ServerListener.
	 * @param hostname
	 * @param port
	 */
	public ServerListener (String hostname, int port) {
		this (hostname, port, true, false);
	}

	/**
	 * Constructs a new ServerListener.
	 * @param hostname
	 * @param port
	 * @param isProxyEnabled
	 * @param isQueryEnabled
	 */
	public ServerListener (String hostname, int port, boolean isProxyEnabled, boolean isQueryEnabled) {
		this.hostname = hostname;
		this.port = port;
		this.isProxyEnabled = isProxyEnabled;
		this.isQueryEnabled = isQueryEnabled;
	}

	/**
	 * Tries to activate the listener.
	 * @param threadGroup
	 */
	public void bind (EventLoopGroup threadGroup) {
		logger.info ("Requesting server socket for " + this.hostname + ":" + this.port + " ...");

		new ServerBootstrap ()
			.channel (NioServerSocketChannel.class)
			.childAttr (LISTENER_ATTRIBUTE, this)
			.childHandler (new ChannelInitializer ())
			.group (threadGroup)
			.localAddress (this.hostname, this.port)
			.bind ()
				.addListener (new ChannelFutureListener () {

					/**
					 * {@inheritDoc}
					 */
					@Override
					public void operationComplete (ChannelFuture future) throws Exception {
						if (future.isSuccess ()) {
							channel = future.channel ();

							logger.info ("Listening on " + hostname + ":" + port + " (" + (isProxyEnabled && isQueryEnabled ? "query enabled" : "query disabled") + ")");
						} else
							logger.warn ("Could not bind listener on " + hostname + ":" + port, future.cause ());
					}
				});
	}

	/**
	 * Initializes new connections.
	 */
	protected class ChannelInitializer extends io.netty.channel.ChannelInitializer<Channel> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void initChannel (Channel ch) throws Exception {
			// set TOS header
			// Note: This might not be available on older Windows systems
			// A missing TOS header shouldn't cause any problems though
			try {
				ch.config().setOption (ChannelOption.IP_TOS, 0x18);
			} catch ( ChannelException ex ) { }

			// set new allocator
			ch.config ().setAllocator (PooledByteBufAllocator.DEFAULT);

			// initialize protocol state
			MinecraftCodec.setProtocol (ch, ConnectionState.HANDSHAKE);

			// add handlers to pipeline
			ch.pipeline ().addLast (HANDLER_NAME_TIMEOUT, new ReadTimeoutHandler (FlowerpotServer.getInstance ().getConfiguration ().getTimeout (), TimeUnit.MILLISECONDS));
			ch.pipeline ().addLast (HANDLER_NAME_FRAME_CODEC, HANDLER_FRAME_CODEC);
			ch.pipeline ().addLast (HANDLER_NAME_MINECRAFT, HANDLER_MINECRAFT_CODEC);
			ch.pipeline ().addLast (HANDLER_NAME_CLIENT, new ClientChannelHandler ());
		}
	}
}
