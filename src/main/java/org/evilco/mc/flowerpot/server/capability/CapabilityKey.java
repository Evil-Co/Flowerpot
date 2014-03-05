package org.evilco.mc.flowerpot.server.capability;

import java.util.HashMap;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CapabilityKey<T> {

	/**
	 * Stores all key instances.
	 */
	protected static final Map<String, CapabilityKey<?>> instances = new HashMap<> ();

	/**
	 * Stores the key name.
	 */
	protected String name;

	/**
	 * Constructs a new CapabilityKey.
	 * @param name
	 */
	protected CapabilityKey (String name) {
		this.name = name;
	}

	/**
	 * Returns a singleton instance of the specified CapabilityKey.
	 * @param name
	 * @param <T>
	 * @return
	 */
	public static <T> CapabilityKey<T> valueOf (String name) {
		if (!instances.containsKey (name)) instances.put (name, new CapabilityKey<T> (name));
		return ((CapabilityKey<T>) instances.get (name));
	}
}
