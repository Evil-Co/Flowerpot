package com.evilco.flowerpot.proxy.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AbstractPacket {

	/**
	 * Indicates whether the packet has been handled.
	 */
	protected boolean handled = false;

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
	 * Checks whether the the packet has been handled.
	 * @return
	 */
	public boolean hasHandled () {
		return this.handled;
	}

	/**
	 * Changes the handling state.
	 * @param value
	 */
	public void setHandled (boolean value) {
		this.handled = value;
	}

	/**
	 * Writes a packet.
	 * @param out
	 */
	public abstract void writePacket (ByteBuf out);
}
