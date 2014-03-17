package org.evilco.mc.flowerpot.protocol.packet.data;

import org.evilco.mc.flowerpot.FlowerpotServer;
import org.evilco.mc.flowerpot.chat.TextMessage;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class StatusResponse {

	/**
	 * Stores the server description.
	 */
	public TextMessage description = new TextMessage ("Flowerpot %s (%s)");

	/**
	 * Stores the favicon.
	 */
	public String favicon = null;

	/**
	 * Stores the mod information.
	 */
	public Object modinfo = null;

	/**
	 * Stores the player statistic.
	 */
	public Players players = new Players ();

	/**
	 * Stores the server version.
	 */
	public Version version = new Version ();

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
		 * Indicates that Flowerpot is used.
		 */
		public boolean flowerpot = true;

		/**
		 * Defines the game version.
		 */
		public String name;

		/**
		 * Defines the protocol version.
		 */
		public int protocol = 4;

		/**
		 * Constructs a new Version.
		 */
		public Version () {
			this.name = FlowerpotServer.getMinecraftVersionString ();
		}
	}
}
