package org.evilco.mc.flowerpot.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MinecraftServer {

	/**
	 * Stores the server alias.
	 */
	protected String aliasHostname;

	/**
	 * Stores the server alias port.
	 */
	protected short aliasPort;
	/**
	 * Stores the server capabilities.
	 */
	protected Map<ServerCapability, Object> capabilities = new HashMap<> ();

	/**
	 * Stores the server hostname.
	 */
	protected String hostname;

	/**
	 * Stores the server port.
	 */
	protected short port;

	/**
	 * Constructs a new MinecraftServer.
	 * @param hostname
	 * @param port
	 */
	public MinecraftServer (String hostname, short port, String aliasHostname, short aliasPort) {
		this.hostname = hostname;
		this.port = port;
		this.aliasHostname = aliasHostname;
		this.aliasPort = aliasPort;
	}

	/**
	 * Constructs a new MinecraftServer.
	 * @param hostname
	 * @param port
	 * @param aliasHostname
	 * @param aliasPort
	 */
	public MinecraftServer (String hostname, int port, String aliasHostname, int aliasPort) {
		this (hostname, ((short) port), aliasHostname, ((short) aliasPort));
	}

	/**
	 * Creates a new server connection.
	 * @return
	 */
	public MinecraftServerClient createConnection () {
		return null;
	}

	/**
	 * Returns the alias hostname.
	 * @return
	 */
	public String getAliasHostname () {
		return this.aliasHostname;
	}

	/**
	 * Returns the alias short.
	 * @return
	 */
	public short getAliasPort () {
		return this.aliasPort;
	}

	/**
	 * Returns the server hostname.
	 * @return
	 */
	public String getHostname () {
		return this.hostname;
	}

	/**
	 * Returns the server port.
	 * @return
	 */
	public short getPort () {
		return this.port;
	}

	/**
	 * Returns a specific capability value.
	 * @param capability
	 * @param type
	 * @param <T>
	 * @return
	 */
	public <T> T getCapability (ServerCapability capability, Class<T> type) {
		return ((T) this.capabilities.get (capability));
	}

	/**
	 * Returns a specific capability value.
	 * @param capability
	 * @return
	 */
	public boolean getCapabilityBoolean (ServerCapability capability) {
		return ((Boolean) this.capabilities.get (capability));
	}

	/**
	 * Returns a specific capability value.
	 * @param capability
	 * @return
	 */
	public int getCapabilityInteger (ServerCapability capability) {
		return ((Integer) this.capabilities.get (capability));
	}

	/**
	 * Returns a specific capability value.
	 * @param capability
	 * @return
	 */
	public String getCapabilityString (ServerCapability capability) {
		return ((String) this.capabilities.get (capability));
	}

	/**
	 * Checks whether a server has a specific capability.
	 * @param capability
	 * @return
	 */
	public boolean hasCapability (ServerCapability capability) {
		return (this.capabilities.containsKey (capability));
	}

	/**
	 * Checks all capabilities.
	 * @param capabilityList
	 * @return
	 */
	public boolean hasCapabilities (List<ServerCapability> capabilityList) {
		for (ServerCapability capability : capabilityList) {
			if (!this.hasCapability (capability)) return false;
		}

		return true;
	}
}
