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
	 * Constructs a new empty UnhandledPacket.
	 */
	public UnhandledPacket () {
		super ();
	}

	/**
	 * Constructs a new UnhandledPacket.
	 * @param in
	 */
	public UnhandledPacket (ByteBuf in) {
		super (in);

		this.data = in;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		out.writeBytes (this.data);
	}
}