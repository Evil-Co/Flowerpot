package com.evilco.flowerpot.proxy.configuration;

import com.evilco.flowerpot.proxy.server.ServerList;
import com.evilco.flowerpot.proxy.server.listener.ListenerList;

import java.util.UUID;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IProxyConfiguration {

	/**
	 * Indicates whether the forge scan is enabled.
	 * @return
	 */
	public boolean getForgeScanEnabled ();

	/**
	 * Returns a list of configured listeners.
	 * @return
	 */
	public ListenerList getListenerList ();

	/**
	 * Returns the metrics identifier (or generators a random new one).
	 * @return
	 */
	public UUID getMetricsIdentifier ();

	/**
	 * Indicates whether the user opted out of metrics.
	 * @return
	 */
	public boolean getMetricsOptedOut ();

	/**
	 * Returns a server icon.
	 * @return
	 */
	public byte[] getServerIcon ();

	/**
	 * Returns the server list.
	 * @return
	 */
	public ServerList getServerList ();

	/**
	 * Returns the connection timeout.
	 * @return
	 */
	public int getTimeout ();
}
