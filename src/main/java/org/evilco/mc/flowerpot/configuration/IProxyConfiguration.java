package org.evilco.mc.flowerpot.configuration;

import org.evilco.mc.flowerpot.server.ServerList;
import org.evilco.mc.flowerpot.server.listener.ListenerList;

import java.util.UUID;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IProxyConfiguration {

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
