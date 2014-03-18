package org.evilco.mc.flowerpot.protocol.packet.event;

import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.protocol.ConnectionState;
import org.evilco.mc.flowerpot.protocol.codec.MinecraftCodec;
import org.evilco.mc.flowerpot.protocol.packet.BadPacketException;
import org.evilco.mc.flowerpot.protocol.packet.EncryptionRequestPacket;
import org.evilco.mc.flowerpot.protocol.packet.LoginSuccessPacket;
import org.evilco.mc.flowerpot.protocol.packet.event.annotation.PacketHandler;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ServerPacketHandler {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = LogManager.getLogger (ServerPacketHandler.class);

	/**
	 * Handles encryption requests.
	 * @param channel
	 * @param packet
	 * @throws BadPacketException
	 */
	@PacketHandler (priority = PacketHandlerPriority.LOWEST, side = PacketHandlerSide.SERVER_TO_PROXY)
	public void handle (Channel channel, EncryptionRequestPacket packet) throws BadPacketException {
		// log
		logger.fatal ("Child server requested protocol encryption. Protocol encryption is not available when using Flowerpot. Please switch all of your servers to offline mode.");

		// packet has been handled
		packet.setHandled (true);

		// close
		throw new BadPacketException ("Encryption is not supported when using Flowerpot");
	}

	/**
	 * Handles login success packets.
	 * @param channel
	 * @param packet
	 */
	@PacketHandler (priority = PacketHandlerPriority.LOWEST, side = PacketHandlerSide.SERVER_TO_PROXY)
	public void handle (Channel channel, LoginSuccessPacket packet) {
		// log
		logger.info ("Logged in successfully. Relay is ready.");

		// update state
		MinecraftCodec.setProtocol (channel, ConnectionState.GAME);

		// packet has been handled
		packet.setHandled (true);
	}
}
