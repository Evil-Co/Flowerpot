package com.evilco.flowerpot.api.configuration.server.capability;

import com.google.common.base.Preconditions;

import java.util.*;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CapabilityKey<T> {

	/**
	 * Stores all previously constructed capabilities.
	 */
	protected static final Map<String, CapabilityKey> capabilityKeys = new HashMap<> ();

	/**
	 * Stores the capability name.
	 */
	protected final String name;

	/**
	 * Stores the capability type.
	 */
	protected final Class<T> type;

	/**
	 * Constructs a new CapabilityKey.
	 * @param type
	 */
	protected CapabilityKey (String name, Class<T> type) {
		Preconditions.checkNotNull (name, "name");
		Preconditions.checkNotNull (type, "type");

		this.name = name;
		this.type = type;
	}

	/**
	 * Builds a capability list.
	 * @param keys
	 * @return
	 */
	public static CapabilityList buildCapabilityList (CapabilityKey... keys) {
		return (new CapabilityList (Arrays.asList (keys)));
	}

	/**
	 * Builds a capability map.
	 * @param arguments
	 * @return
	 */
	public static CapabilityMap buildCapabilityMap (Object... arguments) {
		CapabilityMap map = new CapabilityMap ();
		boolean isValue = false;
		CapabilityKey currentKey = null;

		for (Object argument : arguments) {
			// verify
			Preconditions.checkState (((isValue && argument instanceof Capability) || (!isValue && argument instanceof CapabilityKey)));

			// store
			if (!isValue)
				currentKey = ((CapabilityKey) argument);
			else
				map.put (currentKey, ((Capability) argument));

			// revert key/value
			isValue = !isValue;
		}

		return map;
	}

	/**
	 * Returns the capability name.
	 * @return
	 */
	public String getName () {
		return this.name;
	}

	/**
	 * Returns the capability type.
	 * @return
	 */
	public Class<?> getType () {
		return this.type;
	}

	/**
	 * Returns a capability key.
	 * @param name
	 * @param type
	 * @return
	 */
	public <K> CapabilityKey valueOf (String name, Class<K> type) {
		if (!this.capabilityKeys.containsKey (name)) this.capabilityKeys.put (name, new CapabilityKey (name, type));
		return this.capabilityKeys.get (name);
	}
}