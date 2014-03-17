package org.evilco.mc.flowerpot.user;

import io.netty.channel.Channel;
import org.evilco.mc.flowerpot.protocol.packet.AbstractPacket;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class User {

	/**
	 * Stores the connection channel.
	 */
	protected Channel channel;

	/**
	 * Stores the userID.
	 */
	protected String userID;

	/**
	 * Stores the username.
	 */
	protected String username;

	/**
	 * Constructs a new User.
	 * @param username
	 * @param userID
	 */
	public User (Channel channel, String username, String userID) {
		this.channel = channel;
		this.username = username;
		this.userID = userID;
	}

	/**
	 * Returns the connection channel.
	 * @return
	 */
	public Channel getChannel () {
		return this.channel;
	}

	/**
	 * Returns the user ID.
	 * @return
	 */
	public String getUserID () {
		return this.userID;
	}

	/**
	 * Returns the username.
	 * @return
	 */
	public String getUsername () {
		return this.username;
	}

	/**
	 * Sends a packet.
	 * @param packet
	 */
	public void sendPacket (AbstractPacket packet) {
		this.getChannel ().writeAndFlush (packet);
	}
}
