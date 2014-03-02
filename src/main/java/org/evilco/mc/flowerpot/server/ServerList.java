package org.evilco.mc.flowerpot.server;

import java.util.ArrayList;
import java.util.List;

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
	 * @param serverList
	 */
	public ServerList (List<MinecraftServer> serverList) {
		super (serverList);
	}

	/**
	 * Searches a server.
	 * @param hostname
	 * @param port
	 * @return
	 */
	public MinecraftServer matchServer (String hostname, short port) {
		for (MinecraftServer server : this) {
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
	public MinecraftServer matchServer (String hostname, short port, List<ServerCapability> capabilities) {
		for (MinecraftServer server : this) {
			if (!server.getAliasHostname ().equalsIgnoreCase (hostname)) continue;
			if (server.getAliasPort () != port) continue;

			// verify capabilities
			if (server.hasCapabilities (capabilities)) return server;
		}

		return null;
	}
}
