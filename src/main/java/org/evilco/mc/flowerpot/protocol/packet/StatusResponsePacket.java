package org.evilco.mc.flowerpot.protocol.packet;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import org.evilco.mc.flowerpot.protocol.packet.data.StatusResponse;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class StatusResponsePacket extends AbstractPacket {

	/**
	 * Stores an internal gson instance.
	 */
	protected static Gson gson = new Gson ();

	/**
	 * Stores the server status data.
	 */
	protected StatusResponse response;

	/**
	 * Constructs a new instance of StatusResponsePacket.
	 * @param response
	 */
	public StatusResponsePacket (StatusResponse response) {
		super ();

		this.response = response;
	}

	/**
	 * Constructs a new instance of StatusResponsePacket.
	 * @param in
	 */
	public StatusResponsePacket (ByteBuf in) {
		super (in);

		// read JSON string
		String json = PacketUtility.readString (in);

		// decode
		this.response = gson.fromJson (json, StatusResponse.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		// write data
		PacketUtility.writeString (gson.toJson (this.response), out);
	}
}
