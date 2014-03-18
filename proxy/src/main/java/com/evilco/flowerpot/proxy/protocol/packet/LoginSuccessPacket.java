package com.evilco.flowerpot.proxy.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class LoginSuccessPacket extends AbstractPacket {

	/**
	 * Stores the user's username.
	 */
	protected String username;

	/**
	 * Stores the user's UUID.
	 */
	protected String uuid;

	/**
	 * Constructs a new LoginSuccessPacket.
	 * @param uuid
	 * @param username
	 */
	public LoginSuccessPacket (String uuid, String username) {
		super ();

		this.uuid = uuid;
		this.username = username;
	}

	/**
	 * Constructs a new LoginSuccessPacket.
	 * @param in
	 */
	public LoginSuccessPacket (ByteBuf in) {
		super (in);

		this.uuid = PacketUtility.readString (in);
		this.username = PacketUtility.readString (in);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeString (this.uuid, out);
		PacketUtility.writeString (this.username, out);
	}
}
