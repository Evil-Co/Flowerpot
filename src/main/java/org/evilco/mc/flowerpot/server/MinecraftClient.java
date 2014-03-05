package org.evilco.mc.flowerpot.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.protocol.ServerChannelHandler;
import org.evilco.mc.flowerpot.protocol.packet.AbstractPacket;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MinecraftClient {

	/**
	 * Defines the connection timeout.
	 */
	public static final int TIMEOUT = 4000;

	/**
	 * Stores the internal logger instance.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (MinecraftClient.class);

	/**
	 * Stores the client instance.
	 */
	protected Bootstrap client;

	/**
	 * Stores the client channel.
	 */
	protected Channel clientChannel = null;

	/**
	 * Stores the parent channel.
	 */
	protected Channel parent;

	/**
	 *
	 * @param hostname
	 * @param port
	 */
	public MinecraftClient (Channel parent, String username, String hostname, short port, int protocolVersion) {
		this.client = new Bootstrap ();

		// log
		logger.info ("Connecting to %s:%s with protocol version %s.", hostname, port, protocolVersion);

		// set client properties
		this.parent = parent;
		this.client.group (parent.eventLoop ());
		this.client.channel (NioSocketChannel.class);
		this.client.handler (new MinecraftClientInitializer (this, username, hostname, port, protocolVersion));

		// set options
		this.client.option (ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT);

		// connect
		this.client.remoteAddress (hostname, port);
		this.client.connect ();
	}

	/**
	 * Returns the client channel.
	 * @return
	 */
	public Channel getClientChannel () {
		return this.clientChannel;
	}

	/**
	 * Returns the parent channel.
	 * @return
	 */
	public Channel getParent () {
		return this.parent;
	}

	/**
	 * Sends a packet.
	 * @param packet
	 */
	public void sendPacket (AbstractPacket packet) {
		this.parent.writeAndFlush (packet);
	}

	/**
	 * Sends a packet to the server.
	 * @param packet
	 */
	public void sendPacketServer (AbstractPacket packet) {
		this.clientChannel.writeAndFlush (packet);
	}

	/**
	 * Sets the client channel.
	 * @param channel
	 */
	public void setClientChannel (Channel channel) {
		this.clientChannel = channel;
	}
}
