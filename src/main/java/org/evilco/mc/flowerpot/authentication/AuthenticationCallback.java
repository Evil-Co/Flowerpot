package org.evilco.mc.flowerpot.authentication;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface AuthenticationCallback {

	/**
	 * Callback for failed authentications.
	 */
	public void error ();

	/**
	 * Callback for successful authentications.
	 */
	public void success (String uuid);
}
