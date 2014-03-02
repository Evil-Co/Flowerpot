package org.evilco.mc.flowerpot.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.evilco.mc.flowerpot.protocol.EncryptionUtility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MinecraftEncryptionCodec extends ByteToMessageCodec<ByteBuf> {

	/**
	 * Stores the codec cipher.
	 */
	protected Cipher cipher;

	/**
	 * Stores the shared key.
	 */
	protected SecretKey sharedKey;

	/**
	 * Constructs a new MinecraftEncryptionCodec.
	 * @param sharedKey
	 */
	public MinecraftEncryptionCodec (SecretKey sharedKey) throws Exception {
		this.sharedKey = sharedKey;

		// get cipher
		this.cipher = EncryptionUtility.getEncryptionCipher ();

		// initialize ciper
		this.cipher.init (Cipher.DECRYPT_MODE, this.sharedKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void encode (ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int readableBytes = msg.readableBytes ();
		byte[] buffer = new byte[readableBytes];

		// read buffer
		msg.readBytes (buffer);

		// create output buffer
		byte[] heapOut = new byte[this.cipher.getOutputSize (readableBytes)];

		// write finished result
		out.writeBytes (heapOut, 0, this.cipher.update (buffer, 0, readableBytes, heapOut));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// create new buffer
		int readableBytes = in.readableBytes ();
		byte[] buffer = new byte[readableBytes];

		// read buffer
		in.readBytes (buffer);

		// create heap buffer
		ByteBuf heap = ctx.alloc ().heapBuffer (this.cipher.getOutputSize (readableBytes));

		// write data
		heap.writerIndex (this.cipher.update (buffer, 0, readableBytes, heap.array (), heap.arrayOffset ()));

		// return finished version
		out.add (heap);
	}
}
