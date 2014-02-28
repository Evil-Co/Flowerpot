package org.evilco.mc.flowerpot.protocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftCodec;
import org.evilco.mc.flowerpot.protocol.packet.AbstractPacket;
import org.evilco.mc.flowerpot.protocol.packet.HandshakePacket;

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

		// call parent
		super.channelInactive (ctx);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.debug ("Received message of type " + msg.getClass ().getName ());

		// cast message
		AbstractPacket message = ((AbstractPacket) msg);

		// handle packet
		if (message instanceof HandshakePacket) {
			HandshakePacket handshakePacket = ((HandshakePacket) message);

			// log
			logger.debug ("Received handshake from %s: Requested server %s:%s (protocol version %s) with followup state %s.", ctx.channel ().remoteAddress ().toString (), handshakePacket.getServerAddress (), handshakePacket.getServerPort (), handshakePacket.getProtocolVersion (), handshakePacket.getNextState ());

			// switch state
			switch (handshakePacket.getNextState ()) {
				case STATUS:
					MinecraftCodec.setProtocol (ctx, ConnectionState.STATUS);
					break;
				case LOGIN:
					// TODO
					break;
			}
		}
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
		ctx.close ();
	}
}
