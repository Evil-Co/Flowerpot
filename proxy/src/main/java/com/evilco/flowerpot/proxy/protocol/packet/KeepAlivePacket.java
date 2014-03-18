package com.evilco.flowerpot.proxy.protocol.packet;

import io.netty.buffer.ByteBuf;

import java.util.Random;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class KeepAlivePacket extends AbstractPacket {

	/**
	 * Stores the random integer of this ping.
	 */
	protected int data;

	/**
	 * Constructs a new empty KeepAlivePacket.
	 */
	public KeepAlivePacket () {
		super ();

		this.data = (new Random ()).nextInt ();
	}

	/**
	 * Constructs a new KeepAlivePacket.
	 * @param in
	 */
	public KeepAlivePacket (ByteBuf in) {
		super (in);

		this.data = in.readInt ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		out.writeInt (this.data);
	}
}
