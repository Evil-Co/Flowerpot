package com.evilco.flowerpot.proxy.authentication;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IAuthenticationResult {

	/**
	 * Returns the userID associated with the requested account.
	 * @return
	 */
	public String getUserID ();
}
