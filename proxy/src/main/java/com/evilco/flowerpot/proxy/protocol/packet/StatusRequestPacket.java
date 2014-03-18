package com.evilco.flowerpot.proxy.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class StatusRequestPacket extends AbstractPacket {

	/**
	 * Constructs a new empty StatusRequestPacket.
	 */
	public StatusRequestPacket () {
		super ();
	}

	/**
	 * Constructs a new StatusRequestPacket.
	 * @param in
	 */
	public StatusRequestPacket (ByteBuf in) {
		super (in);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) { }
}
