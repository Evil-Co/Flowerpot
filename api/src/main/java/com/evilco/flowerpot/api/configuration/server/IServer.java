package com.evilco.flowerpot.api.configuration.server;

import com.evilco.flowerpot.api.chat.IChatChannel;
import com.evilco.flowerpot.api.configuration.server.capability.Capability;
import com.evilco.flowerpot.api.configuration.server.capability.CapabilityKey;
import com.evilco.flowerpot.api.configuration.server.capability.CapabilityList;
import com.evilco.flowerpot.api.configuration.server.capability.CapabilityMap;

import java.util.List;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IServer {

	/**
	 * Returns a list of server aliases.
	 * @return
	 */
	public List<IServerAlias> getAliases ();

	/**
	 * Returns all capabilities.
	 * @return
	 */
	public Map<CapabilityKey, Capability> getCapabilities ();

	/**
	 * Returns a list of default channels.
	 * @return
	 */
	public List<IChatChannel> getDefaultChatChannels ();

	/**
	 * Returns the server hostname.
	 * @return
	 */
	public String getHostname ();

	/**
	 * Returns the server port.
	 * @return
	 */
	public short getPort ();

	/**
	 * Checks whether a server has a matching alias.
	 * @param hostname
	 * @param port
	 * @return
	 */
	public boolean hasMatchingAlias (String hostname, short port);

	/**
	 * Checks whether the server has a specific capability.
	 * @param capability
	 * @return
	 */
	public boolean hasCapability (CapabilityKey capability);

	/**
	 * Checks whether the server has a specific capability.
	 * @param key
	 * @param capability
	 * @return
	 */
	public boolean hasCapability (CapabilityKey key, Capability capability);

	/**
	 * Checks whether the server has all of the listed capabilities.
	 * @param capabilities
	 * @return
	 */
	public boolean hasCapabilities (CapabilityList capabilities);

	/**
	 * Checks whether the server has all of the listed capabilities.
	 * @param capabilities
	 * @return
	 */
	public boolean hasCapabilities (CapabilityMap capabilities);

	/**
	 * Checks whether chat channel overrides are enabled.
	 * @return
	 */
	public boolean isChatChannelOverrideEnabled ();

	/**
	 * Registers a new alias.
	 * @param hostname
	 * @param port
	 * @return
	 */
	public IServerAlias registerAlias (String hostname, short port);

	/**
	 * Registers a new capability.
	 * @param key
	 * @param value
	 * @param <T>
	 * @return
	 */
	public <T> Capability<T> registerCapability (CapabilityKey<T> key, T value);

	/**
	 * Sets whether chat channel overrides are enabled.
	 * @param value
	 */
	public void setChannelOverrideEnabled (boolean value);

	/**
	 * Sets a new hostname.
	 * @param hostname
	 */
	public void setHostname (String hostname);

	/**
	 * Sets a new port.
	 * @param port
	 */
	public void setPort (short port);

	/**
	 * Un-Registers an alias.
	 * @param alias
	 */
	public void unregisterAlias (IServerAlias alias);

	/**
	 * Un-Registers an alias.
	 * @param hostname
	 * @param port
	 */
	public void unregisterAlias (String hostname, short port);

	/**
	 * Un-Registers a capability.
	 * @param key
	 */
	public void unregisterCapability (CapabilityKey key);
}