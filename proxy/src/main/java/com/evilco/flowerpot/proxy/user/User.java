package com.evilco.flowerpot.proxy.user;

import io.netty.channel.Channel;
import com.evilco.flowerpot.proxy.chat.channel.ChatChannel;
import com.evilco.flowerpot.proxy.protocol.packet.AbstractPacket;

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
	 * Stores the chat channel the user is currently in.
	 */
	protected ChatChannel chatChannel = null;

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
	 * Returns the chat channel the user is in.
	 * @return
	 */
	public ChatChannel getChatChannel () {
		return this.chatChannel;
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

	/**
	 * Sets a new chat channel.
	 * @param chatChannel
	 */
	public void setChatChannel (ChatChannel chatChannel) {
		this.chatChannel = chatChannel;
	}
}
