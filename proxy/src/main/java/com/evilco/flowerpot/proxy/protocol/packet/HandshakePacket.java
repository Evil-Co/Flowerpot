package com.evilco.flowerpot.proxy.protocol.packet;

import io.netty.buffer.ByteBuf;
import com.evilco.flowerpot.proxy.protocol.packet.data.HandshakeState;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class HandshakePacket extends AbstractPacket {

	/**
	 * Stores the followup state.
	 */
	protected HandshakeState nextState;

	/**
	 * Stores the selected protocol version.
	 */
	protected int protocolVersion;

	/**
	 * Stores the hostname queried.
	 */
	protected String serverAddress;

	/**
	 * Stores the server port queried.
	 */
	protected short serverPort;

	/**
	 * Constructs a new handshake packet.
	 * @param protocolVersion
	 * @param serverAddress
	 * @param serverPort
	 * @param nextState
	 */
	public HandshakePacket (int protocolVersion, String serverAddress, short serverPort, HandshakeState nextState) {
		this.protocolVersion = protocolVersion;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.nextState = nextState;
	}

	/**
	 * De-Serializes a handshake packet.
	 * @param in
	 */
	public HandshakePacket (ByteBuf in) {
		super (in);

		this.protocolVersion = PacketUtility.readVarInt (in);
		this.serverAddress = PacketUtility.readString (in);
		this.serverPort = in.readShort ();
		this.nextState = HandshakeState.valueOf (PacketUtility.readVarInt (in));
	}

	/**
	 * Returns the followup status requested.
	 * @return
	 */
	public HandshakeState getNextState () {
		return this.nextState;
	}

	/**
	 * Returns the selected protocol version.
	 * @return
	 */
	public int getProtocolVersion () {
		return this.protocolVersion;
	}

	/**
	 * Returns the server address requested.
	 * @return
	 */
	public String getServerAddress () {
		return this.serverAddress;
	}

	/**
	 * Returns the server port requested.
	 * @return
	 */
	public short getServerPort () {
		return this.serverPort;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writePacket (ByteBuf out) {
		PacketUtility.writeVarInt (this.protocolVersion, out);
		PacketUtility.writeString (this.serverAddress, out);
		out.writeShort (this.serverPort);
		PacketUtility.writeVarInt (this.nextState.value, out);
	}
}
