package org.evilco.mc.flowerpot.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.evilco.mc.flowerpot.protocol.ConnectionState;
import org.evilco.mc.flowerpot.protocol.ServerChannelHandler;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftCodec;
import org.evilco.mc.flowerpot.protocol.codec.VarIntFrameCodec;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MinecraftClientInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * Stores the parent client instance.
	 */
	protected MinecraftClient client;

	/**
	 * Stores the target hostname.
	 */
	protected String hostname;

	/**
	 * Stores the target port.
	 */
	protected short port;

	/**
	 * Stores the target protocol version.
	 */
	protected int protocolVersion;

	/**
	 * Stores the username.
	 */
	protected String username;

	/**
	 * Constructs a new MinecraftClientInitializer.
	 * @param client
	 * @param hostname
	 * @param port
	 * @param protocolVersion
	 */
	public MinecraftClientInitializer (MinecraftClient client, String username, String hostname, short port, int protocolVersion) {
		super ();

		this.client = client;
		this.username = username;
		this.hostname = hostname;
		this.port = port;
		this.protocolVersion = protocolVersion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initChannel (SocketChannel ch) throws Exception {
		MinecraftCodec.setProtocol (ch, ConnectionState.HANDSHAKE);

		ch.pipeline ().addLast (new VarIntFrameCodec ());
		ch.pipeline ().addLast (new MinecraftCodec (true));
		ch.pipeline ().addLast (new ServerChannelHandler (this.client, this.username, this.hostname, this.port, this.protocolVersion));
	}
}
