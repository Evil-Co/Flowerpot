package org.evilco.mc.flowerpot.configuration;

import org.evilco.mc.flowerpot.server.ServerList;
import org.evilco.mc.flowerpot.server.listener.ListenerList;

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
