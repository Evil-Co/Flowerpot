package org.evilco.mc.flowerpot.protocol.packet;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import org.evilco.mc.flowerpot.chat.message.BaseMessage;
import org.evilco.mc.flowerpot.chat.message.TextMessage;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class KickPacket extends AbstractPacket {

	/**
	 * Stores the gson parser instance for this packet.
	 */
	protected static final Gson gson = new Gson ();

	/**
	 * Stores the kick message.
	 */
	protected BaseMessage message;

	/**
	 * Constructs a new KickPacket.
	 * @param message
	 */
	public KickPacket (String message) {
		super ();

		this.message = new TextMessage (message);
	}

	/**
	 * Constructs a new KickPacket.
	 * @param message
	 */
	public KickPacket (BaseMessage message) {
		super ();

		this.message = message;
	}

	/**
	 * Constructs a new KickPacket.
	 * @param in
	 */
	public KickPacket (ByteBuf in) {
		super (in);

		this.message = gson.fromJson (PacketUtility.readString (in), BaseMessage.class);
	}

	/**
	 * Returns the kick message.
	 * @return
	 */
	public BaseMessage getMessage () {
		return this.message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeString (gson.toJson (this.message), out);
	}
}
