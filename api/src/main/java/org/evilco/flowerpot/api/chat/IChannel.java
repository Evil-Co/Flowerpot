/*
 * Copyright (C) 2014 Johannes Donath <johannesd@evil-co.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.evilco.flowerpot.api.chat;

import org.evilco.flowerpot.api.chat.message.BaseChatMessage;
import org.evilco.flowerpot.api.player.IPlayer;

/**
 * Provides an interface which represents a channel.
 */
public interface IChannel {

	/**
	 * Returns the channel name.
	 * @return The name.
	 */
	public String getName ();

	/**
	 * Sends a message to the channel.
	 * @param message The message.
	 */
	public void sendMessage (BaseChatMessage message);

	/**
	 * Subscribes a player to the channel.
	 * @param player The player to add.
	 */
	public void subscribePlayer (IPlayer player);

	/**
	 * Un-subscribes a player from the channel.
	 * @param player The player to remove.
	 */
	public void unsubscribePlayer (IPlayer player);
}