package com.evilco.flowerpot.api.configuration.server.capability;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class Capability<T> {

	/**
	 * Stores the capability type.
	 */
	protected final Class<T> type;

	/**
	 * Stores the capability value.
	 */
	protected T value = null;

	/**
	 * Constructs a new Capability.
	 * @param type
	 */
	public Capability (Class<T> type) {
		this (type, null);
	}

	/**
	 * Constructs a new Capability.
	 * @param type
	 * @param value
	 */
	public Capability (Class<T> type, T value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns the capability value.
	 * @return
	 */
	public T get () {
		return this.value;
	}

	/**
	 * Returns the capability type.
	 * @return
	 */
	public Class<T> getType () {
		return this.type;
	}

	/**
	 * Sets a new capability value.
	 * @param value
	 */
	public void set (T value) {
		this.value = value;
	}

	/**
	 * Checks whether two capabilities match.
	 * @param capability
	 * @return
	 */
	public boolean equals (Capability capability) {
		if (!this.getType ().equals (capability.getType ())) return false;
		if (this.get () == null && capability.get () == null) return true;
		if (this.get () != null && this.get ().equals (capability.get ())) return true;
		return false;
	}
}