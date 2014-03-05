package org.evilco.mc.flowerpot.protocol;

import com.google.common.base.Charsets;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.FlowerpotServer;
import org.evilco.mc.flowerpot.authentication.AuthenticationCallback;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftCodec;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftEncryptionCodec;
import org.evilco.mc.flowerpot.protocol.packet.*;
import org.evilco.mc.flowerpot.protocol.packet.data.StatusResponse;
import org.evilco.mc.flowerpot.server.MinecraftClient;
import org.evilco.mc.flowerpot.server.MinecraftServer;
import org.evilco.mc.flowerpot.server.capability.Capability;
import org.evilco.mc.flowerpot.server.capability.CapabilityKey;
import org.evilco.mc.flowerpot.server.listener.ServerListener;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

			this.client.getClientChannel ().writeAndFlush (new KickPacket ("disconnect.quit"));
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
		if (message instanceof HandshakePacket) {
			HandshakePacket handshakePacket = ((HandshakePacket) message);

			// log
			logger.debug ("Received handshake from %s: Requested server %s:%s (protocol version %s) with followup state %s.", ctx.channel ().remoteAddress ().toString (), handshakePacket.getServerAddress (), handshakePacket.getServerPort (), handshakePacket.getProtocolVersion (), handshakePacket.getNextState ());

			// get server
			Map<CapabilityKey<?>, Capability<?>> capabilityMap = new HashMap<> ();
			capabilityMap.put (MinecraftServer.CAPABILITY_PROTOCOL, new Capability<Integer> (handshakePacket.getProtocolVersion ()));

			MinecraftServer server = FlowerpotServer.getInstance ().getConfiguration ().getServerList ().matchServer (handshakePacket.getServerAddress (), handshakePacket.getServerPort (), capabilityMap);

			if (server == null) server = FlowerpotServer.getInstance ().getConfiguration ().getServerList ().matchServer (handshakePacket.getServerAddress (), handshakePacket.getServerPort (), Arrays.asList (new CapabilityKey<?>[] {
				MinecraftServer.CAPABILITY_FALLBACK
			}));

			// handle errors
			if (server == null) {
				// log
				logger.fatal ("Could not find any matching server for request " + handshakePacket.getServerAddress () + ":" + handshakePacket.getServerPort () + " (protocol version " + handshakePacket.getProtocolVersion () + ").");

				// close connection
				ctx.channel ().close ();

				// stop further execution
				return;
			}

			// store server
			this.server = server;

			// switch state
			switch (handshakePacket.getNextState ()) {
				case STATUS:
					MinecraftCodec.setProtocol (ctx, ConnectionState.STATUS);
					break;
				case LOGIN:
					MinecraftCodec.setProtocol (ctx, ConnectionState.LOGIN);

					// verify protocol version
					if (handshakePacket.getProtocolVersion () != ((Capability<Integer>) this.server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get ()) {
						// log
						logger.info ("Disconnecting %s: The client version does not match the server version (expected version %s but received %s).", ctx.channel ().remoteAddress (), ((Capability<Integer>) this.server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get (), handshakePacket.getProtocolVersion ());

						// disconnect
						String reason = (((Capability<Integer>) this.server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get () < handshakePacket.getProtocolVersion () ? "disconnect.outdatedServer" : "disconnect.outdatedClient");
						ctx.writeAndFlush (new KickPacket (FlowerpotServer.getInstance ().getTranslation (reason)));
						ctx.channel ().close ();
					}

					break;
			}

			// packet has been handled
			message.setHandled (true);
		}

		if (message instanceof StatusRequestPacket) {
			// log
			logger.debug ("Received status request from %s. Replying with correct server status (protocol version %s).", ctx.channel ().remoteAddress ().toString (), ((Capability<Integer>) this.server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get ());

			// create data object
			StatusResponse response = new StatusResponse ();

			// add encoded favicon
			// TODO: Add missing properties
			response.favicon = FlowerpotServer.getInstance ().getEncodedServerIcon ();

			// pass data to description
			response.description.setText (String.format (response.description.getText (), FlowerpotServer.VERSION, FlowerpotServer.BUILD));

			// send packet
			ctx.writeAndFlush (new StatusResponsePacket (response));

			// packet has been handled
			message.setHandled (true);
		}

		if (message instanceof PingPacket) {
			PingPacket ping = ((PingPacket) message);

			// log
			logger.debug ("Received ping from %s. Replying with pong (%s).", ctx.channel ().remoteAddress ().toString (), ping.getTimestamp ());

			// respond
			ctx.writeAndFlush (ping);

			// packet has been handled
			message.setHandled (true);
		}

		if (message instanceof LoginStartPacket) {
			LoginStartPacket loginStartPacket = ((LoginStartPacket) message);

			// store username
			this.username = loginStartPacket.getUsername ();

			// log
			logger.trace ("User %s requested to be authenticated (account %s has been requested).", ctx.channel ().remoteAddress ().toString (), loginStartPacket.getUsername ());

			// TODO: Allow servers to run in offline mode

			// encryption
			EncryptionRequestPacket encryptionRequestPacket = new EncryptionRequestPacket ();

			// store token
			this.verifyToken = encryptionRequestPacket.getVerifyToken ();
			this.serverID = encryptionRequestPacket.getServerID ();

			// send encryption request
			ctx.writeAndFlush (encryptionRequestPacket);

			// packet has been handled
			message.setHandled (true);
		}

		if (message instanceof EncryptionResponsePacket) {
			EncryptionResponsePacket encryptionResponsePacket = ((EncryptionResponsePacket) message);

			// get encryption key
			SecretKey encryptionKey = encryptionResponsePacket.getSharedKey (FlowerpotServer.getInstance ().getKeyPair ().getPrivate (), this.verifyToken);

			// log
			logger.trace ("User %s replied with encryption key of size %s bytes. Switching to protocol encryption.", ctx.channel ().remoteAddress ().toString (), encryptionKey.getEncoded ().length);

			// switch to encryption
			ctx.channel ().pipeline ().addBefore (ServerListener.HANDLER_NAME_FRAME_CODEC, ServerListener.HANDLER_NAME_ENCRYPTION_CODEC, new MinecraftEncryptionCodec (encryptionKey));

			// authenticate
			FlowerpotServer.getInstance ().getAuthenticationService ().authenticate (this.username, this.serverID.getBytes (Charsets.UTF_8), encryptionKey.getEncoded (), FlowerpotServer.getInstance ().getKeyPair ().getPublic ().getEncoded (), ctx.channel ().eventLoop (), new AuthenticationCallback () {

				/**
				 * {@inheritDoc}
				 */
				@Override
				public void error () {
					ctx.channel ().writeAndFlush (new KickPacket (FlowerpotServer.getInstance ().getTranslation ("disconnect.authenticationServiceFailure")));
				}

				/**
				 * {@inheritDoc}
				 */
				@Override
				public void success (String uuid) {
					if (uuid == null)
						ctx.channel ().writeAndFlush (new KickPacket (FlowerpotServer.getInstance ().getTranslation ("disconnect.authenticationFailure")));
					else {
						// TODO: Kick already connected players with the same name

						// send success packet
						LoginSuccessPacket successPacket = new LoginSuccessPacket (uuid, username);
						ctx.channel ().writeAndFlush (successPacket);

						// update protocol
						logger.info ("%s successfully authenticated with user ID %s.", username, uuid);
						MinecraftCodec.setProtocol (ctx.channel (), ConnectionState.GAME);

						// create client
						client = server.createConnection (ctx.channel (), username, ((Capability<Integer>) server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get ());
					}
				}
			});

			// packet has been handled
			message.setHandled (true);
		}

		// relay packet
		if (!message.hasHandled () && this.client != null) this.client.sendPacketServer (message);
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
