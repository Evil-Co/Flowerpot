package com.evilco.flowerpot.api.chat;

import com.evilco.flowerpot.api.chat.message.BaseMessage;
import com.evilco.flowerpot.api.user.IUser;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IChatManager {

	/**
	 * Returns the channel based on the supplied name.
	 * @param name
	 * @return
	 */
	public IChatChannel getChannel (String name);

	/**
	 * Returns the channel the supplied user is currently in.
	 * @param user
	 * @return
	 */
	public IChatChannel getChannel (IUser user);

	/**
	 * Registers a new channel.
	 * @param channel
	 */
	public void registerChannel (IChatChannel channel);

	/**
	 * Handles a message of a user.
	 * @param user
	 * @param message
	 */
	public void handleMessage (IUser user, BaseMessage message);
}