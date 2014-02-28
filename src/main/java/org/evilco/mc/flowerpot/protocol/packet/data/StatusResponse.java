package org.evilco.mc.flowerpot.protocol.packet.data;

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
		public String text = "Flowerpot %s";
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
		public String name = "1.7.5";

		/**
		 * Defines the protocol version.
		 */
		public int protocol = 0;
	}
}
