package com.evilco.flowerpot.proxy.protocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.evilco.flowerpot.proxy.FlowerpotServer;
import com.evilco.flowerpot.proxy.protocol.codec.MinecraftCodec;
import com.evilco.flowerpot.proxy.protocol.packet.*;
import com.evilco.flowerpot.proxy.protocol.packet.data.HandshakeState;
import com.evilco.flowerpot.proxy.protocol.packet.event.PacketHandlerSide;
import com.evilco.flowerpot.proxy.server.MinecraftClient;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ServerChannelHandler extends ChannelHandlerAdapter {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (ServerChannelHandler.class);

	/**
	 * Stores the parent client instance.
	 */
	protected MinecraftClient client;

	/**
	 * Stores the hostname this client is connected to.
	 */
	protected String hostname;

	/**
	 * Stores the port this client is connected to.
	 */
	protected short port;

	/**
	 * Stores the protocol version requested.
	 */
	protected int protocolVersion;

	/**
	 * Stores the username.
	 */
	protected String username;

	/**
	 *
	 * @param hostname
	 * @param port
	 * @param protocolVersion
	 */
	public ServerChannelHandler (MinecraftClient client, String username, String hostname, short port, int protocolVersion) {
		super ();

		this.client = client;
		this.hostname = hostname;
		this.port = port;
		this.protocolVersion = protocolVersion;
		this.username = username;
	}

	@Override
	public void channelActive (ChannelHandlerContext ctx) throws Exception {
		super.channelActive (ctx);

		// store channel
		this.client.setClientChannel (ctx.channel ());

		// log
		logger.info ("Connecting to server %s with protocol version %s.", ctx.channel ().remoteAddress (), this.protocolVersion);

		// create & send handshake packet
		HandshakePacket handshakePacket = new HandshakePacket (this.protocolVersion, this.hostname, this.port, HandshakeState.LOGIN);
		ctx.channel ().writeAndFlush (handshakePacket);

		// update protocol state
		MinecraftCodec.setProtocol (ctx.channel (), ConnectionState.LOGIN);

		// create & send login start packet
		LoginStartPacket loginStartPacket = new LoginStartPacket (this.username);
		ctx.channel ().writeAndFlush (loginStartPacket);
	}

	@Override
	public void channelInactive (ChannelHandlerContext ctx) throws Exception {
		// log
		logger.info ("Disconnecting from server %s.", ctx.channel ().remoteAddress ());

		// call parent
		super.channelInactive (ctx);
	}

	@Override
	public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead (ctx, msg);

		// cast
		AbstractPacket message = ((AbstractPacket) msg);

		// handle packet
		FlowerpotServer.getInstance ().getPacketManager ().handlePacket (ctx.channel (), message, PacketHandlerSide.SERVER_TO_PROXY);

		// relay unhandled packets
		if (!message.hasHandled ()) this.client.sendPacket (message);
	}

	@Override
	public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
		this.client.sendPacket (new KickPacket ("Internal Error: " + cause.getMessage ()));
		this.client.getParent ().close ();

		logger.warn ("Disconnected client from server due to internal exception.", cause);
	}

	@Override
	public void write (ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// handle packet
		if (msg instanceof AbstractPacket) FlowerpotServer.getInstance ().getPacketManager ().handlePacket (ctx.channel (), ((AbstractPacket) msg), PacketHandlerSide.PROXY_TO_SERVER);

		// write data
		super.write (ctx, msg, promise);
	}
}
