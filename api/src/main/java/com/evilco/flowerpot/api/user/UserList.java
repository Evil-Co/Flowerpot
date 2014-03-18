package com.evilco.flowerpot.api.user;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserList extends ArrayList<IUser> {

	/**
	 * Constructs a new UserList instance.
	 * @param initialCapacity
	 */
	public UserList (int initialCapacity) {
		super (initialCapacity);
	}

	/**
	 * Constructs a new UserList instance.
	 */
	public UserList () {
		super ();
	}

	/**
	 * Constructs a new UserList instance.
	 * @param c
	 */
	public UserList (Collection<? extends IUser> c) {
		super (c);
	}
}
