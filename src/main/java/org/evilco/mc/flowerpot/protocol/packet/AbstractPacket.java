package org.evilco.mc.flowerpot.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AbstractPacket {

	/**
	 * Defines the default constructor.
	 */
	public AbstractPacket () { }

	/**
	 * Defines the serialization constructor.
	 * @param in
	 */
	public AbstractPacket (ByteBuf in) { }

	/**
	 * Writes a packet.
	 * @param out
	 */
	public abstract void writePacket (ByteBuf out);
}
