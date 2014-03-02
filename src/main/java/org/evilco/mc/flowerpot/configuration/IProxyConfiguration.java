package org.evilco.mc.flowerpot.configuration;

import org.evilco.mc.flowerpot.protocol.ServerListener;
import org.evilco.mc.flowerpot.server.MinecraftServer;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IProxyConfiguration {

	/**
	 * Returns the default Minecraft server.
	 * @return
	 */
	public MinecraftServer getDefaultServer ();

	/**
	 * Returns a list of configured listeners.
	 * @return
	 */
	public List<ServerListener> getListenerList ();

	/**
	 * Returns the selected protocol version.
	 * @return
	 */
	public int getProtocolVersion ();

	/**
	 * Returns a server icon.
	 * @return
	 */
	public byte[] getServerIcon ();

	/**
	 * Returns the connection timeout.
	 * @return
	 */
	public int getTimeout ();
}
