package org.evilco.mc.flowerpot.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.protocol.ConnectionState;
import org.evilco.mc.flowerpot.protocol.packet.PacketUtility;
import org.evilco.mc.flowerpot.protocol.packet.AbstractPacket;
import org.evilco.mc.flowerpot.protocol.packet.BadPacketException;
import org.evilco.mc.flowerpot.protocol.packet.UnhandledPacket;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@ChannelHandler.Sharable
public class MinecraftCodec extends ByteToMessageCodec<AbstractPacket> {

	/**
	 * Defines the attribute key for protocol storage.
	 */
	public static final AttributeKey<ConnectionState> ATTRIBUTE_PROTOCOL = AttributeKey.valueOf ("MinecraftProtocol");

	/**
	 * Stores the internal logger instance.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (MinecraftCodec.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void encode (ChannelHandlerContext ctx, AbstractPacket msg, ByteBuf out) throws Exception {
		// get packet ID
		int packetID = getProtocol (ctx).getOutboundRegistry ().getPacketID (msg.getClass ());

		// log
		logger.trace ("Sending packet with ID 0x%02X (packet type is %s)", packetID, msg.getClass ().getName ()); // DEBUG: This debug code will only work out as long as all packetIDs stay below ID 255

		// write packetID
		PacketUtility.writeVarInt (packetID, out);

		// write data
		msg.writePacket (out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// copy packet (for passing through)
		ByteBuf packetData = in.copy ();

		// read packetID
		int packetID = PacketUtility.readVarInt (in);

		logger.trace ("Received a packet with ID 0x%02X.", packetID); // DEBUG: This debug code will only work out as long as all packetIDs stay below ID 255

		// check protocol
		if (getProtocol (ctx).getInboundRegistry ().hasPacket (packetID)) {
			// read packet
			AbstractPacket packet = getProtocol (ctx).getInboundRegistry ().readPacket (packetID, in);

			// debug
			logger.trace ("Packet is assigned to type %s in current protocol state (%s).", packet.getClass ().getName (), getProtocol (ctx).toString ());

			// verify
			if (in.readableBytes () > 0) throw new BadPacketException ("Received extra data after the end of the packet");

			// append packet
			out.add (packet);

			// end execution here
			return;
		}

		// skip data
		in.skipBytes (in.readableBytes ());

		// create packet wrapper for later handling
		out.add (new UnhandledPacket (packetData));
	}

	/**
	 * Returns the corresponding protocol.
	 * @param channel
	 * @return
	 */
	public static ConnectionState getProtocol (Channel channel) {
		Attribute<ConnectionState> stateAttribute = channel.attr (ATTRIBUTE_PROTOCOL);
		return stateAttribute.get ();
	}

	/**
	 * Returns the corresponding protocol.
	 * @param ctx
	 * @return
	 */
	public static ConnectionState getProtocol (ChannelHandlerContext ctx) {
		return getProtocol (ctx.channel ());
	}

	/**
	 * Sets a new protocol.
	 * @param channel
	 * @param state
	 */
	public static void setProtocol (Channel channel, ConnectionState state) {
		logger.debug ("Switching user %s to connection state %s.", channel.remoteAddress (), state);

		Attribute<ConnectionState> stateAttribute = channel.attr (ATTRIBUTE_PROTOCOL);
		stateAttribute.set (state);
	}

	/**
	 * Sets a new protocol.
	 * @param ctx
	 * @param state
	 */
	public static void setProtocol (ChannelHandlerContext ctx, ConnectionState state) {
		setProtocol (ctx.channel (), state);
	}
}
