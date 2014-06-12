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

/**
 * Provides an interface which represents a client to server connection.
 */
public interface IPlayerConnection {

	/**
	 * Terminates the connection.
	 */
	public void disconnect ();

	/**
	 * Returns the player which initialized this connection.
	 * @return The player.
	 */
	public IPlayer getPlayer ();

	/**
	 * Returns the server the connection leads to.
	 * @return The server.
	 */
	public IServer getServer ();

	/**
	 * Sends a message to the server.
	 * @param message The message.
	 */
	public void sendMessage (BaseChatMessage message);

	/**
	 * Sends a packet to the server.
	 * @param packet The packet.
	 */
	public void sendPacket (IPacket packet);
}