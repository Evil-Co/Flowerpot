package org.evilco.mc.flowerpot.authentication;

import io.netty.channel.EventLoop;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IAuthenticationService {

	/**
	 * Authenticates a user.
	 * @param username
	 * @param serverID
	 * @param sharedKey
	 * @param publicKey
	 * @param callback
	 */
	public void authenticate (String username, byte[] serverID, byte[] sharedKey, byte[] publicKey, EventLoop eventLoop, AuthenticationCallback callback) throws Exception;
}
