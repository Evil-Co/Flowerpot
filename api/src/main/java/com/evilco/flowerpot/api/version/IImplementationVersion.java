package com.evilco.flowerpot.api.version;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IImplementationVersion {

	/**
	 * Returns the Minecraft version based on the protocol.
	 * @param protocol
	 * @return
	 */
	public String getMinecraftVersion (int protocol);

	/**
	 * Returns the Flowerpot version.
	 * @return
	 */
	public String getServerVersion ();

	/**
	 * Checks whether a specific protocol version is supported.
	 * @param protocol
	 * @return
	 */
	public boolean isProtocolSupported (int protocol);
}