package com.evilco.flowerpot.api.configuration.listener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ListenerList extends ArrayList<IListener> {

	/**
	 * Constructs a new ListenerList.
	 * @param initialCapacity
	 */
	public ListenerList (int initialCapacity) {
		super (initialCapacity);
	}

	/**
	 * Constructs a new ListenerList.
	 */
	public ListenerList () {
		super ();
	}

	/**
	 * Constructs a new ListenerList.
	 * @param c
	 */
	public ListenerList (Collection<? extends IListener> c) {
		super (c);
	}
}