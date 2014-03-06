package org.evilco.mc.flowerpot.protocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.ReadTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.FlowerpotServer;
import org.evilco.mc.flowerpot.protocol.packet.AbstractPacket;
import org.evilco.mc.flowerpot.protocol.packet.event.PacketHandlerSide;
import org.evilco.mc.flowerpot.server.MinecraftClient;
import org.evilco.mc.flowerpot.server.MinecraftServer;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ClientChannelHandler extends ChannelHandlerAdapter {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (ClientChannelHandler.class);

	/**
	 * Stores the current client instance.
	 */
	protected MinecraftClient client = null;

	/**
	 * Stores the verify token.
	 */
	protected byte[] verifyToken = null;

	/**
	 * Stores the selected Minecraft server.
	 */
	protected MinecraftServer server = null;

	/**
	 * Stores the serverID.
	 */
	protected String serverID = null;

	/**
	 * Stores the requested player name.
	 */
	protected String username = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void channelActive (ChannelHandlerContext ctx) throws Exception {
		// log connection
		logger.info ("Incoming connection from %s.", ctx.channel ().remoteAddress ().toString ());

		// call parent
		super.channelActive (ctx);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void channelInactive (ChannelHandlerContext ctx) throws Exception {
		// log disconnect
		logger.info ("%s disconnected.", ctx.channel ().remoteAddress ().toString ());

		// disconnect from server
		if (this.client != null) {
			logger.debug ("Disconnecting %s from current server.", ctx.channel ().remoteAddress ().toString ());
			this.client.getClientChannel ().close ();
		}

		// call parent
		super.channelInactive (ctx);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void channelRead (final ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.debug ("Received message of type " + msg.getClass ().getName ());

		// cast message
		AbstractPacket message = ((AbstractPacket) msg);

		// handle packet
		FlowerpotServer.getInstance ().getPacketManager ().handlePacket (ctx.channel (), message, PacketHandlerSide.CLIENT_TO_PROXY);

		// relay packet
		if (!message.hasHandled () && this.client != null) this.client.sendPacketServer (message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write (ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (msg instanceof AbstractPacket) FlowerpotServer.getInstance ().getPacketManager ().handlePacket (ctx.channel (), ((AbstractPacket) msg), PacketHandlerSide.PROXY_TO_CLIENT);
		super.write (ctx, msg, promise);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// log
		if (cause instanceof ReadTimeoutException)
			logger.info ("Forcefully Disconnecting %s: Read Timeout", ctx.channel ().remoteAddress ().toString ());
		else
			logger.info ("Forcefully Disconnecting %s: Internal Server Error (%s)", ctx.channel ().remoteAddress ().toString (), cause.getMessage ());

		// log debug information
		logger.debug ("An error occurred inside of a client connection.", cause);

		// close connection
		ctx.channel ().close ();
	}
}
