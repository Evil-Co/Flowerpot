package org.evilco.mc.flowerpot.server.capability;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class Capability<T> {

	/**
	 * Stores the capability value.
	 */
	protected T value = null;

	/**
	 * Constructs a new Capability.
	 * @param value
	 */
	public Capability (T value) {
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
	 * Sets a new value.
	 * @param value
	 */
	public void set (T value) {
		this.value = value;
	}

	/**
	 * Checks whether two capability values match.
	 * @param obj
	 * @return
	 */
	public boolean equals (Capability obj) {
		if (this.get () == null && obj.get () == null) return true;
		if (this.get () != null && obj.get () != null) return true;
		if (this.get () != null && obj.get () != null && this.get ().equals (obj.get ())) return true;
		return false;
	}
}
