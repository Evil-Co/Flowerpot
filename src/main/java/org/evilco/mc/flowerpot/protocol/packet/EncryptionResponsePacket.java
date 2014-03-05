package org.evilco.mc.flowerpot.protocol.packet;

import io.netty.buffer.ByteBuf;
import org.evilco.mc.flowerpot.protocol.EncryptionUtility;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class EncryptionResponsePacket extends AbstractPacket {

	/**
	 * Stores the shared encryption key.
	 */
	protected byte[] sharedKey;

	/**
	 * Stores the verify token.
	 */
	protected byte[] verifyToken;

	/**
	 * Constructs a new EncryptionResponsePacket.
	 */
	public EncryptionResponsePacket (PublicKey publicKey, byte[] verifyToken) {
		super ();

		// generate new shared key
		this.sharedKey = new byte[16];

		// encrypt keys
		this.sharedKey = EncryptionUtility.encryptKey (publicKey, this.sharedKey);
		this.verifyToken = EncryptionUtility.encryptKey (publicKey, verifyToken);
	}

	/**
	 * Constructs a new EncryptionResponsePacket.
	 * @param in
	 */
	public EncryptionResponsePacket (ByteBuf in) {
		super (in);

		// read shared key
		short sharedKeyLength = in.readShort ();
		this.sharedKey = new byte[sharedKeyLength];
		in.readBytes (this.sharedKey);

		// read verification token
		short verifyTokenLength = in.readShort ();
		this.verifyToken = new byte[verifyTokenLength];
		in.readBytes (this.verifyToken);
	}

	/**
	 * Returns the shared key.
	 * @param key
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public SecretKey getSharedKey (PrivateKey key, byte[] token) throws Exception {
		// verify connectivity
		byte[] verifyToken = EncryptionUtility.decryptKey (key, this.verifyToken);

		// verify token
		if (!Arrays.equals (token, verifyToken)) throw new BadPacketException ("The verification tokens do not match");

		// decrypt shared key
		return new SecretKeySpec (EncryptionUtility.decryptKey (key, this.sharedKey), EncryptionUtility.ALGORITHM_ENCRYPTION);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		// write shared key
		out.writeShort (this.sharedKey.length);
		out.writeBytes (this.sharedKey);

		// write verification token
		out.writeShort (this.verifyToken.length);
		out.writeBytes (this.verifyToken);
	}
}
