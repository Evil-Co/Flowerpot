package com.evilco.flowerpot.api.configuration.server;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IServerAlias {

	/**
	 * Returns the alias hostname.
	 * @return
	 */
	public String getHostname ();

	/**
	 * Returns the alias port.
	 * @return
	 */
	public short getPort ();

	/**
	 * Sets a new hostname.
	 * @param hostname
	 */
	public void setHostname (String hostname);

	/**
	 * Sets a new port.
	 * @param port
	 */
	public void setPort (short port);
}