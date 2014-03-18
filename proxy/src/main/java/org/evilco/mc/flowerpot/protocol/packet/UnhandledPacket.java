package org.evilco.mc.flowerpot.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UnhandledPacket extends AbstractPacket {

	/**
	 * Stores the packet contents.
	 */
	protected ByteBuf data = null;

	/**
	 * Stores the packet ID.
	 */
	protected int packetID = -1;

	/**
	 * Constructs a new empty UnhandledPacket.
	 */
	public UnhandledPacket () {
		super ();
	}

	/**
	 * Constructs a new UnhandledPacket.
	 * @param in
	 */
	public UnhandledPacket (int packetID, ByteBuf in) {
		super (in);

		this.packetID = packetID;
		this.data = in;
	}

	/**
	 * Returns the packet ID.
	 * @return
	 */
	public int getPacketID () {
		return packetID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeVarInt (this.packetID, out);
		out.writeBytes (this.data);
	}
}
