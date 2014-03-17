package org.evilco.mc.flowerpot.protocol.packet;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import org.evilco.mc.flowerpot.chat.message.BaseMessage;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ChatPacket extends AbstractPacket {

	/**
	 * Stores the internal gson instance.
	 */
	protected static Gson gson = new Gson ();

	/**
	 * Stores the message to transmit.
	 */
	protected BaseMessage message;

	/**
	 * Constructs a new ChatPacket instance.
	 */
	public ChatPacket (BaseMessage message) {
		super ();

		this.message = message;
	}

	/**
	 * Constructs a new ChatPacket instance.
	 * @param in
	 */
	public ChatPacket (ByteBuf in) {
		super (in);

		// TODO: Implement packet decoding
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeString (gson.toJson (this.message), out);
	}
}
