package org.evilco.mc.flowerpot.user;

import java.util.HashMap;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserManager {

	/**
	 * Stores the user map.
	 */
	protected final Map<String, User> userMap = new HashMap<> ();

	/**
	 * Adds a new user.
	 * @param user
	 */
	public void addUser (User user) {
		this.userMap.put (user.getUsername (), user);
	}

	/**
	 * Returns a user object for a specific user.
	 * @param username
	 */
	public User getUser (String username) {
		return this.userMap.get (username);
	}

	/**
	 * Returns the amount of users currently connected.
	 * @return
	 */
	public int getUserCount () {
		return this.userMap.size ();
	}

	/**
	 * Checks whether a specific user is connected.
	 * @param username
	 * @return
	 */
	public boolean hasUser (String username) {
		return this.userMap.containsKey (username);
	}

	/**
	 * Removes a user from the server.
	 * @param username
	 */
	public void removeUser (String username) {
		this.userMap.remove (username);
	}
}
