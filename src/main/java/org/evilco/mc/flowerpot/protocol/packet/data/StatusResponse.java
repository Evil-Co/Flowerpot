package org.evilco.mc.flowerpot.protocol.packet.data;

import org.evilco.mc.flowerpot.FlowerpotServer;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class StatusResponse {

	/**
	 * Stores the server description.
	 */
	public Description description = new Description ();

	/**
	 * Stores the favicon.
	 */
	public String favicon = null;

	/**
	 * Stores the player statistic.
	 */
	public Players players = new Players ();

	/**
	 * Stores the server version.
	 */
	public Version version = new Version ();

	/**
	 * Stores the server description.
	 */
	public static class Description {

		/**
		 * Defines the server description text.
		 */
		public String text = "Flowerpot %s (%s)";
	}

	/**
	 * Stores the player statistic.
	 */
	public static class Players {

		/**
		 * Defines the max amount of players allowed to connect.
		 */
		public int max = 10000;

		/**
		 * Defines the amount of players connected.
		 */
		public int online = 0;
	}

	/**
	 * Stores the version information of this server.
	 */
	public static class Version {

		/**
		 * Defines the game version.
		 */
		public String name;

		/**
		 * Defines the protocol version.
		 */
		public int protocol = 4; // XXX: Update depending on latest release

		/**
		 * Constructs a new Version.
		 */
		public Version () {
			this.name = FlowerpotServer.getMinecraftVersionString ();
		}
	}
}
