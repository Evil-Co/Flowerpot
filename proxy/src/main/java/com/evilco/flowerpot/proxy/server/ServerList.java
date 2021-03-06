package com.evilco.flowerpot.proxy.server;

import com.evilco.flowerpot.proxy.server.capability.CapabilityKey;
import com.evilco.flowerpot.proxy.server.capability.ICapability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ServerList extends ArrayList<MinecraftServer> {

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
	public ServerList (Collection<? extends MinecraftServer> c) {
		super (c);
	}

	/**
	 * Searches a server.
	 * @param hostname
	 * @param port
	 * @return
	 */
	public MinecraftServer matchServer (String hostname, short port) {
		for (MinecraftServer server : this) {
			if (server.getAliasHostname ().equalsIgnoreCase ("*") && server.getAliasPort () == -1) return server;
			if (server.getAliasHostname ().equalsIgnoreCase (hostname) && server.getAliasPort () == port) return server;
		}

		return null;
	}

	/**
	 * Searches a server.
	 * @param hostname
	 * @param port
	 * @param capabilities
	 * @return
	 */
	public MinecraftServer matchServer (String hostname, short port, List<CapabilityKey<?>> capabilities) {
		for (MinecraftServer server : this) {
			if (!server.getAliasHostname ().equalsIgnoreCase (hostname) && !server.getAliasHostname ().equalsIgnoreCase ("*")) continue;
			if (server.getAliasPort () != port && server.getAliasPort () != -1) continue;

			// verify capabilities
			if (server.hasCapabilities (capabilities)) return server;
		}

		return null;
	}

	/**
	 * Searches a server.
	 * @param hostname
	 * @param port
	 * @param capabilities
	 * @return
	 */
	public MinecraftServer matchServer (String hostname, short port, Map<CapabilityKey<?>, ICapability<?>> capabilities) {
		for (MinecraftServer server : this) {
			if (!server.getAliasHostname ().equalsIgnoreCase (hostname) && !server.getAliasHostname ().equalsIgnoreCase ("*")) continue;
			if (server.getAliasPort () != port && server.getAliasPort () != -1) continue;

			// verify capabilities
			if (server.hasCapabilities (capabilities)) return server;
		}

		return null;
	}
}
