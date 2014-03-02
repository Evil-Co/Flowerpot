package org.evilco.mc.flowerpot.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PingPacket extends AbstractPacket {

	/**
	 * Stores the ping timestamp.
	 */
	protected long timestamp;

	/**
	 * Constructs a new PingPacket.
	 */
	public PingPacket () {
		super ();

		this.timestamp = (System.currentTimeMillis () / 1000L);
	}

	/**
	 * Constructs a new PingPacket.
	 */
	public PingPacket (ByteBuf in) {
		super (in);

		this.timestamp = in.readLong ();
	}

	/**
	 * Returns the ping timestamp.
	 * @return
	 */
	public long getTimestamp () {
		return this.timestamp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		out.writeLong (this.timestamp);
	}
}
