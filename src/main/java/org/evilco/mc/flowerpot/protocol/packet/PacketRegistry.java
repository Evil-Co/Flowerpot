package org.evilco.mc.flowerpot.protocol.packet;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import org.evilco.mc.flowerpot.protocol.ConnectionDirection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PacketRegistry {

	/**
	 * Defines the maximum packet ID.
	 */
	public static final int MAX_PACKET_ID = 0xFF;

	/**
	 * Stores the proxy direction.
	 */
	protected final ConnectionDirection direction;

	/**
	 * Stores all registered packets.
	 */
	protected Class<? extends AbstractPacket>[] registeredPackets = new Class[MAX_PACKET_ID];

	/**
	 * Constructs a protocol.
	 * @param direction
	 */
	public PacketRegistry (ConnectionDirection direction) {
		this.direction = direction;
	}

	/**
	 * Returns the protocol direction.
	 * @return
	 */
	public ConnectionDirection getDirection () {
		return this.direction;
	}

	/**
	 * Returns the packet ID which corresponds to a specific packet.
	 * @param packetClass
	 * @return
	 */
	public int getPacketID (Class<? extends AbstractPacket> packetClass) {
		for (int i = 0; i < this.registeredPackets.length; i++) {
			if (this.registeredPackets[i] == null) continue;
			if (this.registeredPackets[i].equals (packetClass)) return i;
		}

		return -1;
	}

	/**
	 * Checks whether a specific packet is registered.
	 * @param packetID
	 * @return
	 */
	public boolean hasPacket (int packetID) {
		return (packetID <= MAX_PACKET_ID && this.registeredPackets[packetID] != null);
	}

	/**
	 * Reads a packet.
	 * @param packetID
	 * @param data
	 * @return
	 * @throws NoSuchMethodException
	 */
	public AbstractPacket readPacket (int packetID, ByteBuf data) throws NoSuchMethodException {
		// verify arguments
		Preconditions.checkArgument (this.hasPacket (packetID), "No packet is registered for packetID %s for the selected protocol state", packetID);

		// get instance
		Class<? extends AbstractPacket> packetClass = this.registeredPackets[packetID];

		// find proper constructor
		Constructor<? extends AbstractPacket> packetSerializationConstructor = packetClass.getConstructor (ByteBuf.class);

		// make constructor accessible
		packetSerializationConstructor.setAccessible (true);

		// create a new instance
		// FIXME: Add exceptions here to report the problem
		try {
			return packetSerializationConstructor.newInstance (data);
		} catch (Exception ex) {
			throw new IllegalArgumentException (ex);
		}
	}

	/**
	 * Registers a new packet.
	 * @param packetID
	 * @param packet
	 */
	public void registerPacket (int packetID, Class<? extends AbstractPacket> packet) {
		this.registeredPackets[packetID] = packet;
	}
}
