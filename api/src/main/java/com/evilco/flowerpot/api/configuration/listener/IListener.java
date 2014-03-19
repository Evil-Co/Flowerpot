package com.evilco.flowerpot.api.configuration.listener;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IListener {

	/**
	 * Returns the listener hostname.
	 * @return
	 */
	public String getHostname ();

	/**
	 * Returns the listener port.
	 * @return
	 */
	public short getPort ();

	/**
	 * Indicates whether the proxy is enabled on this listener.
	 * @return
	 */
	public boolean isProxyEnabled ();

	/**
	 * Indicates whether queries are enabled on this listener.
	 * @return
	 */
	public boolean isQueryEnabled ();
}