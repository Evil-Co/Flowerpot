package com.evilco.flowerpot.api.configuration.server.capability;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CapabilityList extends ArrayList<CapabilityKey> {

	/**
	 * Constructs a new CapabilityList.
	 * @param initialCapacity
	 */
	public CapabilityList (int initialCapacity) {
		super (initialCapacity);
	}

	/**
	 * Constructs a new CapabilityList.
	 */
	public CapabilityList () {
		super ();
	}

	/**
	 * Constructs a new CapabilityList.
	 * @param c
	 */
	public CapabilityList (Collection<? extends CapabilityKey> c) {
		super (c);
	}
}
