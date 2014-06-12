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

package org.evilco.flowerpot.api.player;

import org.evilco.flowerpot.api.chat.message.BaseChatMessage;
import org.evilco.flowerpot.api.server.IServer;
import org.evilco.mc.protocol.common.packet.IPacket;

import java.util.UUID;

/**
 * Provides an interface which represents a player.
 */
public interface IPlayer {

	/**
	 * Disconnects the player.
	 * @param message The disconnect reason to send.
	 */
	public void disconnect (BaseChatMessage message);

	/**
	 * Returns the player's display name.
	 * @return The display name.
	 */
	public String getDisplayName ();

	/**
	 * Returns the player's globally unique identifier.
	 * @return The identifier.
	 */
	public UUID getPlayerID ();

	/**
	 * Returns the server the player is currently connected to.
	 * @return The server.
	 */
	public IServer getServer ();

	/**
	 * Returns the active player connection.
	 * @return The player connection.
	 */
	public IPlayerConnection getConnection ();

	/**
	 * Sends a message to the player.
	 * @param message The message to send.
	 */
	public void sendMessage (BaseChatMessage message);

	/**
	 * Sends a packet to the player.
	 * @param packet The packet.
	 */
	public void sendPacket (IPacket packet);

	/**
	 * Switches to a different server.
	 * @param server The server to switch to.
	 */
	public void switchServer (IServer server);
}