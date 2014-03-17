package org.evilco.mc.flowerpot.protocol.packet;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import org.evilco.mc.flowerpot.chat.message.BaseMessage;
import org.evilco.mc.flowerpot.chat.message.TextMessage;

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

		// read json string
		String json = PacketUtility.readString (in);

		// decode message
		this.message = BaseMessage.deserialize (json);
	}

	/**
	 * Returns the chat message.
	 * @return
	 */
	public BaseMessage getMessage () {
		return this.message;
	}

	/**
	 * Sets a new message.
	 * @param message
	 */
	public void setMessage (BaseMessage message) {
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeString (gson.toJson (this.message), out);
	}
}
