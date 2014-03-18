package org.evilco.mc.flowerpot.chat.channel;

import com.google.common.base.Preconditions;
import org.evilco.mc.flowerpot.chat.message.BaseMessage;
import org.evilco.mc.flowerpot.protocol.packet.ChatPacket;
import org.evilco.mc.flowerpot.user.User;
import org.evilco.mc.flowerpot.user.UserList;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ChatChannel {

	/**
	 * Stores the channel name.
	 */
	protected String name;

	/**
	 * Stores all users in this channel.
	 */
	protected UserList userList;

	/**
	 * Constructs a new ChatChannel.
	 * @param name
	 */
	public ChatChannel (String name) {
		Preconditions.checkNotNull (name);

		this.name = name;
		this.userList = new UserList ();
	}

	/**
	 * Constructs a new ChatChannel.
	 * @param name
	 * @param userList
	 */
	public ChatChannel (String name, UserList userList) {
		Preconditions.checkNotNull (name, "name");
		Preconditions.checkNotNull (userList, "userList");

		this.name = name;
		this.userList = userList;
	}

	/**
	 * Returns the channel name.
	 * @return
	 */
	public String getName () {
		return this.name;
	}

	/**
	 * Returns the channel user list.
	 * @return
	 */
	public UserList getUserList () {
		return this.userList;
	}

	/**
	 * Lets a user join the channel.
	 * @param user
	 */
	public void join (User user) {
		this.userList.add (user);
		user.setChatChannel (this);
	}

	/**
	 * Sends a message to a specific channel.
	 * @param message
	 */
	public void sendMessage (BaseMessage message) {
		for (User user : this.userList) {
			user.sendPacket (new ChatPacket (message));
		}
	}
}
