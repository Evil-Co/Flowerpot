package org.evilco.mc.flowerpot.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.evilco.mc.flowerpot.protocol.EncryptionUtility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MinecraftEncryptionCodec extends ByteToMessageCodec<ByteBuf> {

	/**
	 * Stores the codec cipher.
	 */
	protected Cipher decryptionCipher;

	/**
	 * Stores the codec cipher.
	 */
	protected Cipher encryptionCipher;

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
		this.decryptionCipher = EncryptionUtility.getEncryptionCipher ();
		this.encryptionCipher = EncryptionUtility.getEncryptionCipher ();

		// initialize ciper
		this.decryptionCipher.init (Cipher.DECRYPT_MODE, sharedKey, new IvParameterSpec (sharedKey.getEncoded ()));
		this.encryptionCipher.init (Cipher.ENCRYPT_MODE, sharedKey, new IvParameterSpec (sharedKey.getEncoded ()));
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
		byte[] heapOut = new byte[this.encryptionCipher.getOutputSize (readableBytes)];

		// write finished result
		out.writeBytes (heapOut, 0, this.encryptionCipher.update (buffer, 0, readableBytes, heapOut));
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
		ByteBuf heap = ctx.alloc ().heapBuffer (this.decryptionCipher.getOutputSize (readableBytes));

		// write data
		heap.writerIndex (this.decryptionCipher.update (buffer, 0, readableBytes, heap.array (), heap.arrayOffset ()));

		// return finished version
		out.add (heap);
	}
}
