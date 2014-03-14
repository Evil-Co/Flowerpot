package org.evilco.mc.flowerpot.protocol.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginMessagePacket extends AbstractPacket {

	/**
	 * Stores the channel name.
	 */
	protected String channel;

	/**
	 * Stores the message.
	 */
	protected ByteBuf data;

	/**
	 * Constructs a new PluginMessagePacket.
	 * @param channel
	 * @param data
	 */
	public PluginMessagePacket (String channel, ByteBuf data) {
		super ();

		this.channel = channel;
		this.data = data;
	}

	/**
	 * Constructs a new PluginMessagePacket.
	 * @param channel
	 * @param data
	 */
	public PluginMessagePacket (String channel, byte[] data) {
		this (channel, Unpooled.copiedBuffer (data));
	}

	/**
	 * Constructs a new PluginMessagePacket.
	 * @param in
	 */
	public PluginMessagePacket (ByteBuf in) {
		super (in);

		this.channel = PacketUtility.readString (in);

		// read data
		short length = in.readShort ();
		byte[] data = new byte[length];
		in.readBytes (data);

		// convert
		this.data = Unpooled.copiedBuffer (data);
	}

	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeString (this.channel, out);
		out.writeShort (this.data.capacity ());
		out.writeBytes (this.data);
	}
}
