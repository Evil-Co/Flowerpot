package org.evilco.mc.flowerpot.protocol.packet;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginMessagePacket extends AbstractPacket {

	/**
	 * Defines the name of the channel used for channel registrations (confusing, huh?).
	 */
	public static final String MINECRAFT_CHANNEL_REGISTER = "REGISTER";

	/**
	 * Defines the Flowerpot server communication channel name.
	 */
	public static final String FLOWERPOT_CHANNEL = "Flowerpot";

	/**
	 * Stores the channel registrations required for Forge.
	 */
	public static final String[] FORGE_CHANNEL_REGISTER = new String[] { "FML|HS", "FML" };

	/**
	 * Stores the forge handshake packet.
	 */
	public static final PluginMessagePacket FORGE_HANDSHAKE = new PluginMessagePacket ("FML", new byte[] { 0, 0, 0, 0, 0, 2 });

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

	/**
	 * Constructs a channel registration packet.
	 * @param channels
	 * @return
	 */
	public static PluginMessagePacket createChannelRegistrationPacket (List<String> channels) {
		// create contents
		ByteBuf buffer = Unpooled.buffer ();
		PacketUtility.writeString (Joiner.on ('\0').join (Iterables.concat (channels)), buffer);

		// create packet
		return new PluginMessagePacket (MINECRAFT_CHANNEL_REGISTER, buffer);
	}

	/**
	 * Returns the channel the packet resides in.
	 * @return
	 */
	public String getChannel () {
		return this.channel;
	}

	/**
	 * Returns the packet data.
	 * @return
	 */
	public ByteBuf getData () {
		return this.data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeString (this.channel, out);
		out.writeShort (this.data.capacity ());
		out.writeBytes (this.data);
	}
}
