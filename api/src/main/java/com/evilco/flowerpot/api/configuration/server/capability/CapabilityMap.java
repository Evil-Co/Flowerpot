package com.evilco.flowerpot.api.configuration.server.capability;

import java.util.HashMap;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CapabilityMap extends HashMap<CapabilityKey, Capability> {

	/**
	 * Constructs a new CapabilityMap.
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public CapabilityMap (int initialCapacity, float loadFactor) {
		super (initialCapacity, loadFactor);
	}

	/**
	 * Constructs a new CapabilityMap.
	 * @param initialCapacity
	 */
	public CapabilityMap (int initialCapacity) {
		super (initialCapacity);
	}

	/**
	 * Constructs a new CapabilityMap.
	 */
	public CapabilityMap () {
		super ();
	}

	/**
	 * Constructs a new CapabilityMap.
	 * @param m
	 */
	public CapabilityMap (Map<? extends CapabilityKey, ? extends Capability> m) {
		super (m);
	}
}
