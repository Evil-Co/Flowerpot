package org.evilco.mc.flowerpot.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class LoginStartPacket extends AbstractPacket {

	/**
	 * Stores the username.
	 */
	protected String username;

	/**
	 * Constructs a new LoginStartPacket.
	 */
	public LoginStartPacket (String username) {
		super ();

		this.username = username;
	}

	/**
	 * Constructs a new LoginStartPacket.
	 * @param in
	 */
	public LoginStartPacket (ByteBuf in) {
		super (in);

		this.username = PacketUtility.readString (in);
	}

	/**
	 * Returns the selected username.
	 * @return
	 */
	public String getUsername () {
		return this.username;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeString (this.username, out);
	}
}
