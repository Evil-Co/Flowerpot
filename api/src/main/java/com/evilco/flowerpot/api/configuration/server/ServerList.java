package com.evilco.flowerpot.api.configuration.server;

import com.evilco.flowerpot.api.configuration.server.capability.Capability;
import com.evilco.flowerpot.api.configuration.server.capability.CapabilityKey;
import com.evilco.flowerpot.api.configuration.server.capability.CapabilityList;
import com.evilco.flowerpot.api.configuration.server.capability.CapabilityMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ServerList extends ArrayList<IServer> {

	/**
	 * Constructs a new ServerList.
	 * @param initialCapacity
	 */
	public ServerList (int initialCapacity) {
		super (initialCapacity);
	}

	/**
	 * Constructs a new ServerList.
	 */
	public ServerList () {
		super ();
	}

	/**
	 * Constructs a new ServerList.
	 * @param c
	 */
	public ServerList (Collection<? extends IServer> c) {
		super (c);
	}

	/**
	 * Searches a server based on it's aliases.
	 * @param hostname
	 * @param port
	 * @return
	 */
	public IServer matchServer (String hostname, short port) {
		for (IServer server : this) {
			if (server.hasMatchingAlias (hostname, port)) return server;
		}

		return null;
	}

	/**
	 * Searches a server based on it's capabilities.
	 * @param capability
	 * @return
	 */
	public IServer matchServer (CapabilityKey capability) {
		for (IServer server : this) {
			if (server.hasCapability (capability)) return server;
		}

		return null;
	}

	/**
	 * Searches a server based on it's capabilities.
	 * @param key
	 * @param value
	 * @return
	 */
	public IServer matchServer (CapabilityKey key, Capability value) {
		for (IServer server : this) {
			if (server.hasCapability (key, value)) return server;
		}

		return null;
	}

	/**
	 * Searches a server based on it's capabilities.
	 * @param capabilities
	 * @return
	 */
	public IServer matchServer (CapabilityList capabilities) {
		for (IServer server : this) {
			if (server.hasCapabilities (capabilities)) return server;
		}

		return null;
	}

	/**
	 * Searches a server based on it's capabilities.
	 * @param capabilities
	 * @return
	 */
	public IServer matchServer (CapabilityMap capabilities) {
		for (IServer server : this) {
			if (server.hasCapabilities (capabilities)) return server;
		}

		return null;
	}

	/**
	 * Searches a server based on it's capabilities and alias.
	 * @param hostname
	 * @param port
	 * @param capability
	 * @return
	 */
	public IServer matchServer (String hostname, short port, CapabilityKey capability) {
		for (IServer server : this) {
			if (server.hasMatchingAlias (hostname, port) && server.hasCapability (capability)) return server;
		}

		return null;
	}

	/**
	 * Searches a server based on it's capabilities and alias.
	 * @param hostname
	 * @param port
	 * @param key
	 * @param value
	 * @return
	 */
	public IServer matchServer (String hostname, short port, CapabilityKey key, Capability value) {
		for (IServer server : this) {
			if (server.hasMatchingAlias (hostname, port) && server.hasCapability (key, value)) return server;
		}

		return null;
	}

	/**
	 * Searches a server based on it's capabilities and alias.
	 * @param hostname
	 * @param port
	 * @param capabilities
	 * @return
	 */
	public IServer matchServer (String hostname, short port, CapabilityList capabilities) {
		for (IServer server : this) {
			if (server.hasMatchingAlias (hostname, port) && server.hasCapabilities (capabilities)) return server;
		}

		return null;
	}

	/**
	 * Searches a server based on it's capabilities and alias.
	 * @param hostname
	 * @param port
	 * @param capabilities
	 * @return
	 */
	public IServer matchServer (String hostname, short port, CapabilityMap capabilities) {
		for (IServer server : this) {
			if (server.hasMatchingAlias (hostname, port) && server.hasCapabilities (capabilities)) return server;
		}

		return null;
	}
}