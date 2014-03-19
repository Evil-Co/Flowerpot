package com.evilco.flowerpot.api;

import com.evilco.event.EventManager;
import com.evilco.flowerpot.api.authentication.IAuthenticationService;
import com.evilco.flowerpot.api.chat.IChatManager;
import com.evilco.flowerpot.api.configuration.IConfiguration;
import com.evilco.flowerpot.api.network.INetworkManager;
import com.evilco.flowerpot.api.translation.ITranslationManager;
import com.evilco.flowerpot.api.user.IUserManager;
import com.evilco.flowerpot.api.version.IImplementationVersion;

import java.security.KeyPair;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IProxyServer {

	/**
	 * Starts listening.
	 * @throws Exception
	 */
	public void bind () throws Exception;

	/**
	 * Returns the currently active authentication service.
	 * @return
	 */
	public IAuthenticationService getAuthenticationService ();

	/**
	 * Returns the currently active chat manager.
	 * @return
	 */
	public IChatManager getChatManager ();

	/**
	 * Returns the currently active configuration.
	 * @return
	 */
	public IConfiguration getConfiguration ();

	/**
	 * Returns the encryption key pair.
	 * @return
	 */
	public KeyPair getEncryptionKeyPair ();

	/**
	 * Returns the currently active event manager.
	 * @return
	 */
	public EventManager getEventManager ();

	/**
	 * Returns the currently active network manager.
	 * @return
	 */
	public INetworkManager getNetworkManager ();

	/**
	 * Returns the currently active translation manager.
	 * @return
	 */
	public ITranslationManager getTranslationManager ();

	/**
	 * Returns the currently active user manager.
	 * @return
	 */
	public IUserManager getUserManager ();

	/**
	 * Returns the server implementation version.
	 * @return
	 */
	public IImplementationVersion getVersion ();

	/**
	 * Sets a new authentication service instance.
	 * @param service
	 */
	public void setAuthenticationService (IAuthenticationService service);

	/**
	 * Sets a new chat manager instance.
	 * @param chatManager
	 */
	public void setChatManager (IChatManager chatManager);
}