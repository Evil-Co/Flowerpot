package com.evilco.flowerpot.api.configuration;

import com.evilco.flowerpot.api.configuration.listener.IListener;
import com.evilco.flowerpot.api.configuration.listener.ListenerList;
import com.evilco.flowerpot.api.configuration.server.IServer;
import com.evilco.flowerpot.api.configuration.server.IServerAlias;
import com.evilco.flowerpot.api.configuration.server.ServerList;

import java.util.List;
import java.util.UUID;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IConfiguration {

	/**
	 * Generates a new metrics identifier.
	 * @return
	 */
	public UUID generateMetricsIdentifier ();

	/**
	 * Returns a list of listeners.
	 * @return
	 */
	public ListenerList getListeners ();

	/**
	 * Returns the metrics identifier.
	 * @return
	 */
	public UUID getMetricsIdentifier ();

	/**
	 * Returns the server icon.
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

	/**
	 * Indicates whether the forge scan is enabled.
	 * @return
	 */
	public boolean isForgeScanEnabled ();

	/**
	 * Indicates whether metrics is enabled.
	 * @return
	 */
	public boolean isMetricsEnabled ();

	/**
	 * Registers a new listener.
	 * @param hostname
	 * @param port
	 * @param proxyEnabled
	 * @param queryEnabled
	 * @return
	 */
	public IListener registerListener (String hostname, short port, boolean proxyEnabled, boolean queryEnabled);

	/**
	 * Registers a new server.
	 * @param hostname
	 * @param port
	 * @return
	 */
	public IServer registerServer (String hostname, short port);

	/**
	 * Sets whether forge scanning is enabled.
	 * @param value
	 */
	public void setForgeScanEnabled (boolean value);

	/**
	 * Sets whether metrics are enabled.
	 * @param value
	 */
	public void setMetricsEnabled (boolean value);

	/**
	 * Sets a new metrics identifier.
	 * @param identifier
	 */
	public void setMetricsIdentifier (UUID identifier);

	/**
	 * Sets a new server icon.
	 * @param icon
	 */
	public void setServerIcon (byte[] icon);

	/**
	 * Sets a new timeout.
	 * @param timeout
	 */
	public void setTimeout (int timeout);
}