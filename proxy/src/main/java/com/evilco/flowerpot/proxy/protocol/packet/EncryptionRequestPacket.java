package com.evilco.flowerpot.proxy.protocol.packet;

import io.netty.buffer.ByteBuf;
import com.evilco.flowerpot.proxy.FlowerpotServer;
import sun.security.rsa.RSAPublicKeyImpl;

import java.security.PublicKey;
import java.util.Random;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class EncryptionRequestPacket extends AbstractPacket {

	/**
	 * Stores the public key.
	 */
	protected PublicKey publicKey;

	/**
	 * Stores the serverID.
	 */
	protected String serverID;

	/**
	 * Stores the verify token.
	 */
	protected byte[] verifyToken;

	/**
	 * Creates a new EncryptionRequestPacket.
	 */
	public EncryptionRequestPacket () {
		super ();

		// get random
		Random random = new Random ();

		// generate serverID
		// TODO: Why is it called serverID when we randomize it each time?!
		this.serverID = Long.toString (random.nextLong (), 16);
		this.publicKey = FlowerpotServer.getInstance ().getKeyPair ().getPublic ();
		this.verifyToken = new byte[4];
		random.nextBytes (this.verifyToken);
	}

	/**
	 * Creates a new EncryptionRequestPacket.
	 * @param in
	 * @throws Exception
	 */
	public EncryptionRequestPacket (ByteBuf in) throws Exception{
		super (in);

		// read data
		this.serverID = PacketUtility.readString (in);

		short publicKeyLength = in.readShort ();
		byte[] publicKey = new byte[publicKeyLength];
		in.readBytes (publicKey);

		short verifyTokenLength = in.readShort ();
		this.verifyToken = new byte[verifyTokenLength];
		in.readBytes (this.verifyToken);

		// de-serialize public key
		this.publicKey = new RSAPublicKeyImpl (publicKey);
	}

	/**
	 * Returns the current public key.
	 * @return
	 */
	public PublicKey getPublicKey () {
		return this.publicKey;
	}

	/**
	 * Returns the selected serverID.
	 * @return
	 */
	public String getServerID () {
		return this.serverID;
	}

	/**
	 * Returns the current verify token.
	 * @return
	 */
	public byte[] getVerifyToken () {
		return this.verifyToken;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeString (this.serverID, out);
		out.writeShort (this.publicKey.getEncoded ().length);
		out.writeBytes (this.publicKey.getEncoded ());
		out.writeShort (this.verifyToken.length);
		out.writeBytes (this.verifyToken);
	}
}
