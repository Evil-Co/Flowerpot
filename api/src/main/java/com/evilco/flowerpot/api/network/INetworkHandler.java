package com.evilco.flowerpot.api.network;

import com.evilco.flowerpot.api.network.packet.IPacket;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface INetworkHandler {

	/**
	 * Returns the assigned priority.
	 * @return
	 */
	public NetworkPriority getPriority ();

	/**
	 * Returns the assigned handler side.
	 * @return
	 */
	public NetworkSide getSide ();

	/**
	 * Invokes the network handler.
	 * @param packet
	 * @param side
	 */
	public void invoke (IPacket packet, NetworkSide side);
}
