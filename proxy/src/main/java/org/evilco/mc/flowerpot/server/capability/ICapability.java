package org.evilco.mc.flowerpot.server.capability;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ICapability<T> {

	/**
	 * Returns the capability value.
	 * @return
	 */
	public T get ();

	/**
	 * Sets a new value.
	 * @param value
	 */
	public void set (T value);

	/**
	 * Checks whether two capability values match.
	 * @param obj
	 * @return
	 */
	public boolean equals (ICapability obj);
}
