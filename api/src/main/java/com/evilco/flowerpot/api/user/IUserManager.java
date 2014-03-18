package com.evilco.flowerpot.api.user;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IUserManager {

	/**
	 * Returns a user based on the username.
	 * @param username
	 * @return
	 */
	public IUser getUser (String username);

	/**
	 * Returns all registered users.
	 * @return
	 */
	public UserList getUserList ();

	/**
	 * Registers a new user.
	 * @param user
	 */
	public void registerUser (IUser user);

	/**
	 * Removes a user from the manager.
	 * @param user
	 */
	public void removeUser (IUser user);
}