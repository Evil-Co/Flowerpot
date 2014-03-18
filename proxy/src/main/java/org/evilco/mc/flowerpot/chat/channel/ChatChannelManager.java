package org.evilco.mc.flowerpot.chat.channel;

import org.evilco.mc.flowerpot.chat.message.BaseMessage;
import org.evilco.mc.flowerpot.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ChatChannelManager {

	/**
	 * Stores the channel map.
	 */
	protected Map<String, ChatChannel> channelMap;

	/**
	 * Constructs a new ChatChannelManager.
	 */
	public ChatChannelManager () {
		this.channelMap = new HashMap<> ();
	}

	/**
	 * Registers a new channel.
	 * @param channel
	 */
	public void registerChannel (ChatChannel channel) {
		this.channelMap.put (channel.getName (), channel);
	}

	/**
	 * Registers a new channel.
	 * @param name
	 * @return
	 */
	public ChatChannel registerChannel (String name) {
		ChatChannel channel = new ChatChannel (name);
		this.registerChannel (channel);
		return channel;
	}

	/**
	 * Registers a list of channels.
	 * @param names
	 * @return
	 */
	public List<ChatChannel> registerChannel (List<String> names) {
		List<ChatChannel> channelList = new ArrayList<> ();

		for (String name : names) {
			channelList.add (this.registerChannel (name));
		}

		return channelList;
	}

	/**
	 * Sends a message to a specific channel.
	 * @param user
	 * @param message
	 */
	public void sendMessage (User user, BaseMessage message) {
		user.getChatChannel ().sendMessage (message);
	}
}