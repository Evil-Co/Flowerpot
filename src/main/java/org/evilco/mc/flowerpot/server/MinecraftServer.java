package org.evilco.mc.flowerpot.server;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MinecraftServer {

	/**
	 * Stores the server hostname.
	 */
	protected String hostname;

	/**
	 * Stores the server port.
	 */
	protected int port;

	/**
	 * Constructs a new MinecraftServer.
	 * @param hostname
	 * @param port
	 */
	public MinecraftServer (String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * Creates a new server connection.
	 * @return
	 */
	public MinecraftServerClient createConnection () {
		return null;
	}
}
