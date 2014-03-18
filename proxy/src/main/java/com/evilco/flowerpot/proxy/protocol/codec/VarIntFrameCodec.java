package com.evilco.flowerpot.proxy.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.CorruptedFrameException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.evilco.flowerpot.proxy.protocol.packet.PacketUtility;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class VarIntFrameCodec extends ByteToMessageCodec<ByteBuf> {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = LogManager.getFormatterLogger (VarIntFrameCodec.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void encode (ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		// get body
		int bodyLength = msg.readableBytes ();
		int headerLength = varintSize (bodyLength);

		// re-create packet
		out.ensureWritable (headerLength + bodyLength);

		// write length
		PacketUtility.writeVarInt (bodyLength, out);
		out.writeBytes (msg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// mark index
		in.markReaderIndex ();

		logger.trace ("Received %s bytes.", in.readableBytes ());

		// initialize buffer
		final byte[] buffer = new byte[3];

		for (int i = 0; i < buffer.length; i++) {
			// not enough data is available
			if (!in.isReadable ()) {
				in.resetReaderIndex ();
				return;
			}

			// read one byte
			buffer[i] = in.readByte ();

			// check data
			if (buffer[i] >= 0) {
				// get length
				int length = PacketUtility.readVarInt (Unpooled.wrappedBuffer (buffer));

				// verify bytes
				if (in.readableBytes () < length) {
					// reset index
					in.resetReaderIndex ();

					// wait until enough data is available
					return;
				} else {
					out.add (in.readBytes (length));
					return;
				}
			}
		}

		throw new CorruptedFrameException ("Length wider than 21-bit");
	}

	/**
	 * Returns a variable int.
	 * @param argument
	 * @return
	 */
	protected static int varintSize (int argument) {
		if ((argument & 0xFFFFFF80) == 0) return 1;
		if ((argument & 0xFFFFC000) == 0) return 2;
		if ((argument & 0xFFE00000) == 0) return 3;
		if ((argument & 0xF0000000) == 0) return 4;
		return 5;
	}
}
