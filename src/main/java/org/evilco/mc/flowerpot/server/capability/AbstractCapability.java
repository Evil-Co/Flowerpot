package org.evilco.mc.flowerpot.server.capability;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AbstractCapability<T> implements Capability<T> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals (Capability obj) {
		if (this.get () == null && obj.get () == null) return true;
		if (this.get () != null && obj.get () != null) return true;
		if (this.get () != null && obj.get () != null && this.get ().equals (obj.get ())) return true;
		return false;
	}
}
