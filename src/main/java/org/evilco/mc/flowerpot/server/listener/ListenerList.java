package org.evilco.mc.flowerpot.server.listener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ListenerList extends ArrayList<ServerListener> {

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
	public ListenerList (Collection<? extends ServerListener> c) {
		super (c);
	}
}
