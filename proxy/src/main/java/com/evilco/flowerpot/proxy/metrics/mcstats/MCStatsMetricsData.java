package com.evilco.flowerpot.proxy.metrics.mcstats;

import java.util.UUID;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
class MCStatsMetricsData {

	/**
	 * Stores the amount of cores present.
	 */
	public int cores;

	/**
	 * Stores the server UUID.
	 */
	public String guid;

	/**
	 * Stores the Java version we're on.
	 */
	public String java_version;

	/**
	 * Stores the OS architecture.
	 */
	public String osarch;

	/**
	 * Stores the OS name.
	 */
	public String osname;

	/**
	 * Stores the OS version.
	 */
	public String osversion;

	/**
	 * Indicates whether the request is a ping or not.
	 */
	public int ping;

	/**
	 * Stores the amount of players online.
	 */
	public int players_online;

	/**
	 * Stores the plugin version.
	 */
	public String plugin_version;

	/**
	 * Stores the server version.
	 */
	public String server_version;

	/**
	 * Constructs a new MCStatsMetricsData instance.
	 * @param uuid
	 * @param pluginVersion
	 * @param serverVersion
	 */
	public MCStatsMetricsData (UUID uuid, String pluginVersion, String serverVersion, int playerCount, boolean ping) {
		this.cores = Runtime.getRuntime ().availableProcessors ();
		this.guid = uuid.toString ();
		this.java_version = System.getProperty("java.version");
		this.osarch = System.getProperty("os.arch");
		this.osname = System.getProperty("os.name");
		this.osversion = System.getProperty("os.version");
		this.ping = (ping ? 1 : 0);
		this.players_online = playerCount;
		this.plugin_version = pluginVersion;
		this.server_version = serverVersion;

		// fix variables
		if (this.osarch.equals ("amd64")) this.osarch = "x86_64";
	}
}
