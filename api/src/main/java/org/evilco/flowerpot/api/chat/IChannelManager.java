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

/**
 * Provides a manager for chat channels.
 */
public interface IChannelManager {

	/**
	 * Returns the channel based on it's name.
	 * @param channelName The channel name.
	 * @return The corresponding channel.
	 */
	public IChannel getChannel (String channelName);

	/**
	 * Registers a new channel.
	 * @param channel The channel.
	 */
	public void registerChannel (IChannel channel);

	/**
	 * Registers a new channel.
	 * @param channelName The channel name.
	 * @return The channel created.
	 */
	public IChannel registerChannel (String channelName);

	/**
	 * Unregisters a channel.
	 * @param channel The channel.
	 */
	public void unregisterChannel (IChannel channel);

	/**
	 * Un-Registers a channel.
	 * @param channelName The channel name.
	 */
	public void unregisterChannel (String channelName);
}