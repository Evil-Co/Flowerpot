package com.evilco.flowerpot.api.network.packet;

import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IPacket {

	/**
	 * Indicates whether the packet has been handled.
	 * @return
	 */
	public boolean isHandled ();

	/**
	 * Sets whether the packet has been handled.
	 * @param handled
	 */
	public void setHandled (boolean handled);

	/**
	 * Serializes a packet for transmission.
	 * @param out
	 */
	public void write (ByteBuf out);
}
