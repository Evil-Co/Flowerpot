package com.evilco.flowerpot.api.configuration.server.client;

import com.evilco.flowerpot.api.network.packet.IPacket;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IClient {

	/**
	 * Connects to the server.
	 * @throws Exception
	 */
	public void connect () throws Exception;

	/**
	 * Disconnects from the server.
	 * @throws Exception
	 */
	public void disconnect () throws Exception;

	/**
	 * Sends a packet to the connected server.
	 * @param packet
	 */
	public void sendPacket (IPacket packet);
}