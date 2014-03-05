package org.evilco.mc.flowerpot.protocol;

import org.evilco.mc.flowerpot.protocol.packet.*;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum ConnectionState {
	HANDSHAKE {
		/**
		 * Static Initialization
		 */
		{
			INBOUND.registerPacket (0x00, HandshakePacket.class);
		}
	},
	STATUS {
		/**
		 * Static Initialization
		 */
		{
			INBOUND.registerPacket (0x00, StatusRequestPacket.class);
			INBOUND.registerPacket (0x01, PingPacket.class);

			OUTBOUND.registerPacket (0x00, StatusResponsePacket.class);
			OUTBOUND.registerPacket (0x01, PingPacket.class);
		}
	},
	LOGIN {
		/**
		 * Static Initialization
		 */
		{
			INBOUND.registerPacket (0x00, LoginStartPacket.class);
			INBOUND.registerPacket (0x01, EncryptionResponsePacket.class);

			OUTBOUND.registerPacket (0x00, KickPacket.class);
			OUTBOUND.registerPacket (0x01, EncryptionRequestPacket.class);
			OUTBOUND.registerPacket (0x02, LoginSuccessPacket.class);
		}
	},
	GAME {
		/**
		 * Static Initialization
		 */
		{
			// TODO
		}
	};

	/**
	 * Stores the inbound protocol.
	 */
	protected final PacketRegistry INBOUND = new PacketRegistry (ConnectionDirection.INBOUND);

	/**
	 * Stores the outbound protocol.
	 */
	protected final PacketRegistry OUTBOUND = new PacketRegistry (ConnectionDirection.OUTBOUND);

	/**
	 * Returns the inbound registry.
	 * @param isClient
	 * @return
	 */
	public PacketRegistry getInboundRegistry (boolean isClient) {
		return (isClient ? OUTBOUND : INBOUND);
	}

	/**
	 * Returns the inbound registry.
	 * @return
	 */
	public PacketRegistry getInboundRegistry () {
		return getInboundRegistry (false);
	}

	/**
	 * Stores the outbound registry.
	 * @param isClient
	 * @return
	 */
	public PacketRegistry getOutboundRegistry (boolean isClient) {
		return (isClient ? INBOUND : OUTBOUND);
	}

	/**
	 * Stores the outbound registry.
	 * @return
	 */
	public PacketRegistry getOutboundRegistry () {
		return getOutboundRegistry (false);
	}
}
