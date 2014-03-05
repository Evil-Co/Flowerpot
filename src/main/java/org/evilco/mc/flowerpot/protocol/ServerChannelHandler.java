package org.evilco.mc.flowerpot.protocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftCodec;
import org.evilco.mc.flowerpot.protocol.packet.*;
import org.evilco.mc.flowerpot.protocol.packet.data.HandshakeState;
import org.evilco.mc.flowerpot.server.MinecraftClient;

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

		// check for encryption requests
		if (msg instanceof EncryptionRequestPacket) {
			// log
			logger.fatal ("Child server requested protocol encryption. Protocol encryption is not available when using Flowerpot. Please switch all of your servers to offline mode.");

			// packet has been handled
			message.setHandled (true);

			// close
			throw new BadPacketException ("Encryption is not supported when using Flowerpot");
		}

		// check for login success packet
		if (msg instanceof LoginSuccessPacket) {
			// log
			logger.info ("Logged in successfully. Relay is ready.");

			// update state
			MinecraftCodec.setProtocol (ctx, ConnectionState.GAME);

			// packet has been handled
			message.setHandled (true);
		}

		// relay unhandled packets
		if (!message.hasHandled ()) this.client.sendPacket (message);
	}

	@Override
	public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
		this.client.sendPacket (new KickPacket ("Internal Error: " + cause.getMessage ()));
		this.client.getParent ().close ();

		logger.warn ("Disconnected client from server due to internal exception.", cause);
	}
}
