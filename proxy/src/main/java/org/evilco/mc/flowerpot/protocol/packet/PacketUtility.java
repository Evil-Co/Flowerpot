package org.evilco.mc.flowerpot.protocol.packet;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PacketUtility {

	/**
	 * Reads a string.
	 * @param in
	 * @return
	 */
	public static String readString (ByteBuf in) {
		int length = readVarInt (in);

		// verify packet
		Preconditions.checkArgument (length <= Short.MAX_VALUE, "Cannot process strings longer than Short.MAX_VALUE (received %s characters)", length);

		// create buffer & read data
		byte[] buffer = new byte[length];
		in.readBytes (buffer);

		// create string
		return new String (buffer, Charsets.UTF_8);
	}

	/**
	 * Reads a variable integer.
	 * @param in
	 * @return
	 */
	public static int readVarInt (ByteBuf in) {
		// create buffers
		int out = 0;
		int bytes = 0;
		byte buffer;

		// read all parts
		while (true) {
			// read one more byte
			buffer = in.readByte ();

			// let's have some fun
			out |= (buffer & 0x7F) << (bytes++ * 7);

			// verify size
			if (bytes > 5) throw new RuntimeException ("VarInt exceeds the maximum size");

			// check end
			if ((buffer & 0x80) != 0x80) break;
		}

		return out;
	}

	/**
	 * Writes a string.
	 * @param value
	 * @param out
	 */
	public static void writeString (String value, ByteBuf out) {
		// verify argument
		Preconditions.checkArgument (value.length () <= Short.MAX_VALUE, "Cannot process strings longer than Short.MAX_VALUE (received %s characters)", value.length ());

		// convert to byte array
		byte[] raw = value.getBytes (Charsets.UTF_8);

		// write length
		writeVarInt (raw.length, out);

		// write data
		out.writeBytes (raw);
	}

	/**
	 * Writes a variable integer.
	 * @param value
	 * @param out
	 */
	public static void writeVarInt (int value, ByteBuf out) {
		// initialize variables
		int part;

		// write all parts
		while (true) {
			// get current part
			part = value & 0x7F;

			// remove part from stack
			value >>>= 7;

			// fill
			if (value != 0) part |= 0x80;

			// write output
			out.writeByte (part);

			// check whether the stack is empty
			if (value == 0) break;
		}
	}
}
