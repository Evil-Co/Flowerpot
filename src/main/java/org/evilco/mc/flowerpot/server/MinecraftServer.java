package org.evilco.mc.flowerpot.server;

import io.netty.channel.Channel;
import org.evilco.mc.flowerpot.server.capability.ICapability;
import org.evilco.mc.flowerpot.server.capability.CapabilityKey;

import java.util.List;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class MinecraftServer {

	/**
	 * Defines the fallback capability.
	 */
	public static final CapabilityKey<Void> CAPABILITY_FALLBACK = CapabilityKey.valueOf ("Fallback");

	/**
	 * Defines the offline mode capability.
	 */
	public static final CapabilityKey<Void> CAPABILITY_OFFLINE_MODE = CapabilityKey.valueOf ("OfflineMode");

	/**
	 * Defines the protocol capability.
	 */
	public static final CapabilityKey<Integer> CAPABILITY_PROTOCOL = CapabilityKey.valueOf ("Protocol");

	/**
	 * Adds a new capability to a specific server.
	 * @param capabilityKey
	 * @param capability
	 */
	public abstract void addCapability (CapabilityKey<?> capabilityKey, ICapability<?> capability);

	/**
	 * Creates a new server connection.
	 * @return
	 */
	public MinecraftClient createConnection (Channel channel, String username, int protocolVersion) {
		return new MinecraftClient (channel, username, this.getHostname (), this.getPort (), protocolVersion);
	}

	/**
	 * Returns the alias hostname.
	 * @return
	 */
	public abstract String getAliasHostname ();

	/**
	 * Returns the alias short.
	 * @return
	 */
	public abstract short getAliasPort ();

	/**
	 * Returns the server hostname.
	 * @return
	 */
	public abstract String getHostname ();

	/**
	 * Returns the server port.
	 * @return
	 */
	public abstract short getPort ();

	/**
	 * Returns a capability.
	 * @param capability
	 * @return
	 */
	public abstract ICapability<?> getCapability (CapabilityKey<?> capability);

	/**
	 * Checks whether a server has a specific capability.
	 * @param capability
	 * @return
	 */
	public abstract boolean hasCapability (CapabilityKey<?> capability);

	/**
	 * Checks whether a server has a specific capability.
	 * @param capabilityKey
	 * @param capability
	 * @return
	 */
	public abstract boolean hasCapability (CapabilityKey<?> capabilityKey, ICapability<?> capability);

	/**
	 * Checks all capabilities.
	 * @param capabilityList
	 * @return
	 */
	public abstract boolean hasCapabilities (List<CapabilityKey<?>> capabilityList);

	/**
	 * Checks all capabilities.
	 * @param capabilityMap
	 * @return
	 */
	public abstract boolean hasCapabilities (Map<CapabilityKey<?>, ICapability<?>> capabilityMap);
}
