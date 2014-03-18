package com.evilco.flowerpot.api.network.annotation;

import com.evilco.flowerpot.api.network.NetworkDirection;
import com.evilco.flowerpot.api.network.NetworkPriority;
import com.evilco.flowerpot.api.network.NetworkSide;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public @interface NetworkHandler {

	/**
	 * Defines the handler direction.
	 * @return
	 */
	public NetworkDirection direction () default NetworkDirection.INBOUND;

	/**
	 * Defines the handler priority.
	 * @return
	 */
	public NetworkPriority priority () default NetworkPriority.NORMAL;

	/**
	 * Defines the handler side.
	 * @return
	 */
	public NetworkSide side () default NetworkSide.CLIENT;
}
