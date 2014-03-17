package org.evilco.mc.flowerpot.protocol.packet.event;

import com.google.common.base.Charsets;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.FlowerpotServer;
import org.evilco.mc.flowerpot.authentication.AuthenticationCallback;
import org.evilco.mc.flowerpot.event.login.HandshakeEvent;
import org.evilco.mc.flowerpot.event.login.LoginSuccessEvent;
import org.evilco.mc.flowerpot.event.login.PreLoginEvent;
import org.evilco.mc.flowerpot.event.login.StatusResponseEvent;
import org.evilco.mc.flowerpot.forge.ForgeModInformation;
import org.evilco.mc.flowerpot.protocol.ConnectionState;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftCodec;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftEncryptionCodec;
import org.evilco.mc.flowerpot.protocol.packet.*;
import org.evilco.mc.flowerpot.protocol.packet.data.StatusResponse;
import org.evilco.mc.flowerpot.protocol.packet.event.annotation.PacketHandler;
import org.evilco.mc.flowerpot.server.MinecraftClient;
import org.evilco.mc.flowerpot.server.MinecraftServer;
import org.evilco.mc.flowerpot.server.capability.CapabilityKey;
import org.evilco.mc.flowerpot.server.capability.DefaultCapability;
import org.evilco.mc.flowerpot.server.capability.ICapability;
import org.evilco.mc.flowerpot.server.listener.ServerListener;
import org.evilco.mc.flowerpot.user.User;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ClientPacketHandler {

	/**
	 * Defines the Client attribute key.
	 */
	protected static final AttributeKey<MinecraftClient> ATTRIBUTE_KEY_CLIENT = AttributeKey.valueOf ("Client");

	/**
	 * Defines the MinecraftServer attribute key.
	 */
	protected static final AttributeKey<MinecraftServer> ATTRIBUTE_KEY_SERVER = AttributeKey.valueOf ("Server");

	/**
	 * Defines the server ID attribute key.
	 */
	protected static final AttributeKey<String> ATTRIBUTE_KEY_SERVER_ID = AttributeKey.valueOf ("ServerID");

	/**
	 * Defines the username attribute key.
	 */
	protected static final AttributeKey<String> ATTRIBUTE_KEY_USERNAME = AttributeKey.valueOf ("Username");

	/**
	 * Defines the verify token attribute key.
	 */
	protected static final AttributeKey<byte[]> ATTRIBUTE_KEY_VERIFY_TOKEN = AttributeKey.valueOf ("VerifyToken");

	/**
	 * Stores the internal logger instance.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (ClientPacketHandler.class);

	/**
	 * Returns the current client instance.
	 * @param channel
	 * @return
	 */
	public static MinecraftClient getClient (Channel channel) {
		Attribute<MinecraftClient> clientAttribute = channel.attr (ATTRIBUTE_KEY_CLIENT);
		return clientAttribute.get ();
	}

	/**
	 * Returns the current server instance.
	 * @param channel
	 * @return
	 */
	public static MinecraftServer getServer (Channel channel) {
		Attribute<MinecraftServer> serverAttribute = channel.attr (ATTRIBUTE_KEY_SERVER);
		return serverAttribute.get ();
	}

	/**
	 * Returns the serverID attribute.
	 * @param channel
	 * @return
	 */
	public static String getServerID (Channel channel) {
		Attribute<String> serverIDAttribute = channel.attr (ATTRIBUTE_KEY_SERVER_ID);
		return serverIDAttribute.get ();
	}

	/**
	 * Returns the username attribute.
	 * @param channel
	 * @return
	 */
	public static String getUsername (Channel channel) {
		Attribute<String> usernameAttribute = channel.attr (ATTRIBUTE_KEY_USERNAME);
		return usernameAttribute.get ();
	}

	/**
	 * Returns the verify token attribute.
	 * @param channel
	 * @return
	 */
	public static byte[] getVerifyToken (Channel channel) {
		Attribute<byte[]> verifyTokenAttribute = channel.attr (ATTRIBUTE_KEY_VERIFY_TOKEN);
		return verifyTokenAttribute.get ();
	}

	/**
	 * Handles encryption response packets.
	 * @param channel
	 * @param packet
	 * @throws Exception
	 */
	@PacketHandler (priority = PacketHandlerPriority.LOWEST)
	public void handle (final Channel channel, EncryptionResponsePacket packet) throws Exception {
		// get attributes
		final String username = getUsername (channel);
		final MinecraftServer server = getServer (channel);
		String serverID = getServerID (channel);
		byte[] verifyToken = getVerifyToken (channel);

		// get encryption key
		SecretKey encryptionKey = packet.getSharedKey (FlowerpotServer.getInstance ().getKeyPair ().getPrivate (), verifyToken);

		// log
		logger.trace ("User %s replied with encryption key of size %s bytes. Switching to protocol encryption.", channel.remoteAddress ().toString (), encryptionKey.getEncoded ().length);

		// switch to encryption
		channel.pipeline ().addBefore (ServerListener.HANDLER_NAME_FRAME_CODEC, ServerListener.HANDLER_NAME_ENCRYPTION_CODEC, new MinecraftEncryptionCodec (encryptionKey));

		// authenticate
		FlowerpotServer.getInstance ().getAuthenticationService ().authenticate (username, serverID.getBytes (Charsets.UTF_8), encryptionKey.getEncoded (), FlowerpotServer.getInstance ().getKeyPair ().getPublic ().getEncoded (), channel.eventLoop (), new AuthenticationCallback () {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void error () {
				channel.writeAndFlush (new KickPacket (FlowerpotServer.getInstance ().getTranslation ("disconnect.authenticationServiceFailure")));
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void success (String uuid) {
				if (uuid == null)
					channel.writeAndFlush (new KickPacket (FlowerpotServer.getInstance ().getTranslation ("disconnect.authenticationFailure")));
				else {
					// kick existing players
					if (FlowerpotServer.getInstance ().getUserManager ().hasUser (username)) {
						// send kick packet
						FlowerpotServer.getInstance ().getUserManager ().getUser (username).getChannel ().writeAndFlush (new KickPacket (FlowerpotServer.getInstance ().getTranslation ("disconnect.sessionRenew")));

						// close connection
						FlowerpotServer.getInstance ().getUserManager ().getUser (username).getChannel ().close ();
					}

					// register new player
					FlowerpotServer.getInstance ().getUserManager ().addUser (new User (channel, username, uuid));

					// fire event
					try {
						FlowerpotServer.getInstance ().getEventManager ().fireEvent (new LoginSuccessEvent ());
					} catch (Exception ex) {
						logger.fatal ("Could not fire LoginSuccessEvent", ex);
					}

					// send success packet
					LoginSuccessPacket successPacket = new LoginSuccessPacket (uuid, username);
					channel.writeAndFlush (successPacket);

					// update protocol
					logger.info ("%s successfully authenticated with user ID %s.", username, uuid);
					MinecraftCodec.setProtocol (channel, ConnectionState.GAME);

					// create client
					setClient (channel, server.createConnection (channel, username, ((ICapability<Integer>) server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get ()));
				}
			}
		});

		// packet has been handled
		packet.setHandled (true);
	}

	/**
	 * Handles handshakes.
	 * @param packet
	 */
	@PacketHandler (priority = PacketHandlerPriority.LOWEST)
	public void handle (Channel channel, HandshakePacket packet) throws Exception {
		logger.debug ("Received login from %s: Requested server %s:%s (protocol version %s) with followup state %s.", channel.remoteAddress ().toString (), packet.getServerAddress (), packet.getServerPort (), packet.getProtocolVersion (), packet.getNextState ());

		// get server
		Map<CapabilityKey<?>, ICapability<?>> capabilityMap = new HashMap<> ();
		capabilityMap.put (MinecraftServer.CAPABILITY_PROTOCOL, new DefaultCapability<> (packet.getProtocolVersion ()));

		// fire server search event
		HandshakeEvent handshakeEvent = new HandshakeEvent (capabilityMap);
		FlowerpotServer.getInstance ().getEventManager ().fireEvent (handshakeEvent);

		// get new capability map
		capabilityMap = handshakeEvent.getCapabilityMap ();

		// find server
		MinecraftServer server = FlowerpotServer.getInstance ().getConfiguration ().getServerList ().matchServer (packet.getServerAddress (), packet.getServerPort (), capabilityMap);

		// find fallback server
		if (server == null) server = FlowerpotServer.getInstance ().getConfiguration ().getServerList ().matchServer (packet.getServerAddress (), packet.getServerPort (), Arrays.asList (new CapabilityKey<?>[]{MinecraftServer.CAPABILITY_FALLBACK}));

		// fire pre login event
		PreLoginEvent preLoginEvent = new PreLoginEvent (server);
		FlowerpotServer.getInstance ().getEventManager ().fireEvent (preLoginEvent);

		// replace server
		server = preLoginEvent.getServer ();

		// handle errors
		if (server == null || preLoginEvent.isCancelled ()) {
			// log
			logger.fatal ("Could not find any matching server for request %s:%s (protocol version %s).", packet.getServerAddress (), packet.getServerPort (), packet.getProtocolVersion ());

			// close connection
			channel.close ().awaitUninterruptibly ();
			return;
		}

		// store server
		setServer (channel, server);

		// switch state
		switch (packet.getNextState ()) {
			case STATUS:
				MinecraftCodec.setProtocol (channel, ConnectionState.STATUS);
				break;
			case LOGIN:
				MinecraftCodec.setProtocol (channel, ConnectionState.LOGIN);

				// verify protocol version
				if (packet.getProtocolVersion () != ((ICapability<Integer>) server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get ()) {
					// log
					logger.info ("Disconnecting %s: The client version does not match the server version (expected version %s but received %s).", channel.remoteAddress (), ((ICapability<Integer>) server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get (), packet.getProtocolVersion ());

					// disconnect
					String reason = (((ICapability<Integer>) server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get () < packet.getProtocolVersion () ? "disconnect.outdatedServer" : "disconnect.outdatedClient");
					channel.writeAndFlush (new KickPacket (FlowerpotServer.getInstance ().getTranslation (reason)));
					channel.close ().awaitUninterruptibly ();
				}

				break;
		}

		// packet has been handled
		packet.setHandled (true);
	}

	/**
	 * Handles login start packets.
	 * @param channel
	 * @param packet
	 */
	@PacketHandler (priority = PacketHandlerPriority.LOWEST)
	public void handle (Channel channel, LoginStartPacket packet) throws Exception {
		// get attributes
		MinecraftServer server = getServer (channel);

		// store username
		String username = packet.getUsername ();
		setUsername (channel, username);

		// log
		logger.trace ("User %s requested to be authenticated (account %s has been requested).", channel.remoteAddress ().toString (), packet.getUsername ());

		// check for offline mode
		if (server.hasCapability (MinecraftServer.CAPABILITY_OFFLINE_MODE)) {
			// fire event
			FlowerpotServer.getInstance ().getEventManager ().fireEvent (new LoginSuccessEvent ());

			// kick existing players
			if (FlowerpotServer.getInstance ().getUserManager ().hasUser (username)) {
				// send kick packet
				FlowerpotServer.getInstance ().getUserManager ().getUser (username).getChannel ().writeAndFlush (new KickPacket (FlowerpotServer.getInstance ().getTranslation ("disconnect.sessionRenew")));

				// close connection
				FlowerpotServer.getInstance ().getUserManager ().getUser (username).getChannel ().close ();
			}

			// generate UUID
			MessageDigest digest = MessageDigest.getInstance ("SHA-1");

			// register new player
			FlowerpotServer.getInstance ().getUserManager ().addUser (new User (channel, username, "offline:" + UUID.nameUUIDFromBytes (digest.digest (username.getBytes ("UTF-8"))).toString ()));

			// write success packet
			channel.writeAndFlush (new LoginSuccessPacket (username, UUID.randomUUID ().toString ()));
		} else {
			// encryption
			EncryptionRequestPacket encryptionRequestPacket = new EncryptionRequestPacket ();

			// store token
			setVerifyToken (channel, encryptionRequestPacket.getVerifyToken ());
			setServerID (channel, encryptionRequestPacket.getServerID ());

			// send encryption request
			channel.writeAndFlush (encryptionRequestPacket);
		}

		// packet has been handled
		packet.setHandled (true);
	}

	/**
	 * Handles pings.
	 * @param channel
	 * @param packet
	 */
	@PacketHandler (priority = PacketHandlerPriority.LOWEST)
	public void handle (Channel channel, PingPacket packet) {
		// log
		logger.debug ("Received ping from %s. Replying with pong (%s).", channel.remoteAddress ().toString (), packet.getTimestamp ());

		// respond
		channel.writeAndFlush (packet);

		// packet has been handled
		packet.setHandled (true);
	}

	/**
	 * Handles plugin messages.
	 * @param channel
	 * @param packet
	 */
	@PacketHandler (priority = PacketHandlerPriority.LOWEST)
	public void handle (Channel channel, PluginMessagePacket packet) {
		if (packet.getChannel ().equals (PluginMessagePacket.FLOWERPOT_CHANNEL)) {
			// create kick packet
			channel.writeAndFlush (new KickPacket ("One of your mods tried to use the Flowerpot channel."));

			// packet has been handled
			packet.setHandled (true);
		}
	}

	/**
	 * Handles status request.
	 * @param channel
	 * @param packet
	 */
	@PacketHandler (priority = PacketHandlerPriority.LOWEST)
	public void handle (Channel channel, StatusRequestPacket packet) throws Exception {
		// get server
		MinecraftServer server = getServer (channel);

		// log
		logger.debug ("Received status request from %s. Replying with correct server status (protocol version %s).", channel.remoteAddress ().toString (), ((ICapability<Integer>) server.getCapability (MinecraftServer.CAPABILITY_PROTOCOL)).get ());

		// create data object
		StatusResponse response = new StatusResponse ();

		// add metadata
		// TODO: Add missing properties
		response.favicon = FlowerpotServer.getInstance ().getEncodedServerIcon ();
		response.players.online = FlowerpotServer.getInstance ().getUserManager ().getUserCount ();

		// pass data to description
		response.description.setText (String.format (response.description.getText (), FlowerpotServer.VERSION, FlowerpotServer.BUILD));

		// add forge information
		if (FlowerpotServer.getInstance ().getConfiguration ().getForgeScanEnabled ()) response.modinfo = new ForgeModInformation ();

		// fire event
		StatusResponseEvent statusResponseEvent = new StatusResponseEvent (response);
		FlowerpotServer.getInstance ().getEventManager ().fireEvent (statusResponseEvent);

		// replace response
		response = statusResponseEvent.getResponse ();

		// send packet
		channel.writeAndFlush (new StatusResponsePacket (response));

		// packet has been handled
		packet.setHandled (true);
	}

	/**
	 * Sets a new client.
	 * @param channel
	 * @param client
	 */
	public static void setClient (Channel channel, MinecraftClient client) {
		Attribute<MinecraftClient> clientAttribute = channel.attr (ATTRIBUTE_KEY_CLIENT);
		clientAttribute.set (client);
	}

	/**
	 * Sets a new server.
	 * @param channel
	 * @param server
	 */
	public static void setServer (Channel channel, MinecraftServer server) {
		Attribute<MinecraftServer> serverAttribute = channel.attr (ATTRIBUTE_KEY_SERVER);
		serverAttribute.set (server);
	}

	/**
	 * Sets a new serverID.
	 * @param channel
	 * @param serverID
	 */
	public static void setServerID (Channel channel, String serverID) {
		Attribute<String> serverIDAttribute = channel.attr (ATTRIBUTE_KEY_SERVER_ID);
		serverIDAttribute.set (serverID);
	}

	/**
	 * Sets a new username.
	 * @param channel
	 * @param username
	 */
	public static void setUsername (Channel channel, String username) {
		Attribute<String> usernameAttribute = channel.attr (ATTRIBUTE_KEY_USERNAME);
		usernameAttribute.set (username);
	}

	/**
	 * Sets a new verify token.
	 * @param channel
	 * @param verifyToken
	 */
	public static void setVerifyToken (Channel channel, byte[] verifyToken) {
		Attribute<byte[]> verifyTokenAttribute = channel.attr (ATTRIBUTE_KEY_VERIFY_TOKEN);
		verifyTokenAttribute.set (verifyToken);
	}
}
