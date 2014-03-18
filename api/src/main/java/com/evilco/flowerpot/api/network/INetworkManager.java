package com.evilco.flowerpot.api.network;

import com.evilco.flowerpot.api.network.packet.IPacket;

import java.lang.reflect.Method;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface INetworkManager {

	/**
	 * Posts a packet for handling.
	 * @param packet
	 * @param side
	 */
	public void handlePacket (IPacket packet, HandlerSide side);

	/**
	 * Registers a new network handler.
	 * @param handler
	 */
	public void registerHandler (INetworkHandler handler, HandlerSide side, HandlerPriority priority);

	/**
	 * Registers a new network handler.
	 * @param object
	 * @param method
	 */
	public void registerHandler (Object object, Method method, HandlerSide side, HandlerPriority priority);

	/**
	 * Registers a new network handler object.
	 * @param object
	 */
	public void registerHandler (Object object);
}