package com.evilco.flowerpot.api.chat;

import com.evilco.flowerpot.api.chat.message.BaseMessage;
import com.evilco.flowerpot.api.user.IUser;
import com.evilco.flowerpot.api.user.UserList;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IChatChannel {

	/**
	 * Returns the command abbreviation.
	 * @return
	 */
	public String getCommandAbbreviation ();

	/**
	 * Returns the display name.
	 * @return
	 */
	public String getDisplayName ();

	/**
	 * Returns the channel name.
	 * @return
	 */
	public String getName ();

	/**
	 * Returns a list of subscribed users.
	 * @return
	 */
	public UserList getSubscribedUsers ();

	/**
	 * Sends a message into the channel.
	 * @param user
	 * @param message
	 */
	public void sendMessage (IUser user, BaseMessage message);

	/**
	 * Subscribes a user to the channel.
	 * @param user
	 */
	public void subscribe (IUser user);

	/**
	 * Un-Subscribes a user from the channel.
	 * @param user
	 */
	public void unsubscribe (IUser user);
}
