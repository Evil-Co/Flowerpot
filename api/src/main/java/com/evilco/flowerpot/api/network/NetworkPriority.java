package com.evilco.flowerpot.api.network;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum NetworkPriority {
	LOWEST (-2),
	LOW (-1),
	NORMAL (0),
	HIGH (1),
	HIGHEST (2),
	MONITOR (3);

	/**
	 * Stores the integer representation.
	 */
	public final int value;

	/**
	 * Constructs a new NetworkPriority.
	 * @param value
	 */
	private NetworkPriority (int value) {
		this.value = value;
	}
}
