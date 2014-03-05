package org.evilco.mc.flowerpot.configuration.xml;

import org.evilco.mc.flowerpot.server.MinecraftServer;
import org.evilco.mc.flowerpot.server.capability.Capability;
import org.evilco.mc.flowerpot.server.capability.CapabilityKey;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class XMLServer extends MinecraftServer {

	/**
	 * Stores the alias hostname.
	 */
	@XmlAttribute (name = "aliasHostname", namespace = XMLProxyConfiguration.NAMESPACE)
	protected String aliasHostname = null;

	/**
	 * Stores the alias port.
	 */
	@XmlAttribute (name = "aliasPort", namespace = XMLProxyConfiguration.NAMESPACE)
	protected Short aliasPort = null;

	/**
	 * Stores all associated capabilities.
	 */
	@XmlElementWrapper (name = "capabilities", namespace = XMLProxyConfiguration.NAMESPACE)
	@XmlElement (name = "capability", namespace = XMLProxyConfiguration.NAMESPACE)
	@XmlJavaTypeAdapter (ServerCapabilityMapAdapter.class)
	protected Map<CapabilityKey<?>, Capability<?>> capabilityMap = new HashMap<> ();

	/**
	 * Stores the server hostname.
	 */
	@XmlValue
	protected String hostname = null;

	/**
	 * Stores the server port.
	 */
	@XmlAttribute (name = "port", namespace = XMLProxyConfiguration.NAMESPACE)
	protected Short port = null;

	/**
	 * Constructs a new server.
	 * @param hostname
	 * @param port
	 * @param aliasHostname
	 * @param aliasPort
	 */
	public XMLServer (String hostname, short port, String aliasHostname, short aliasPort) {
		this.hostname = hostname;
		this.port = port;
		this.aliasHostname = aliasHostname;
		this.aliasPort = aliasPort;

		// add default capabilities
		this.capabilityMap.put (MinecraftServer.CAPABILITY_PROTOCOL, new Capability<> (4));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCapability (CapabilityKey<?> capabilityKey, Capability<?> capability) {
		if (this.hasCapability (capabilityKey)) throw new InvalidParameterException ("The supplied capability is already registered with this server.");
		this.capabilityMap.put (capabilityKey, capability);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAliasHostname () {
		return this.aliasHostname;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public short getAliasPort () {
		return this.aliasPort;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getHostname () {
		return this.hostname;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public short getPort () {
		return this.port;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Capability<?> getCapability (CapabilityKey<?> capability) {
		return this.capabilityMap.get (capability);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasCapability (CapabilityKey<?> capability) {
		return this.capabilityMap.containsKey (capability);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasCapability (CapabilityKey<?> capabilityKey, Capability<?> capability) {
		if (!this.hasCapability (capabilityKey)) return false;
		return this.getCapability (capabilityKey).equals (capability);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasCapabilities (List<CapabilityKey<?>> capabilityList) {
		for (CapabilityKey<?> capabilityKey : capabilityList) {
			if (!this.hasCapability (capabilityKey)) return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasCapabilities (Map<CapabilityKey<?>, Capability<?>> capabilityMap) {
		for (Map.Entry<CapabilityKey<?>, Capability<?>> capabilityEntry : capabilityMap.entrySet ()) {
			if (!this.hasCapability (capabilityEntry.getKey (), capabilityEntry.getValue ())) return false;
		}

		return true;
	}
}
