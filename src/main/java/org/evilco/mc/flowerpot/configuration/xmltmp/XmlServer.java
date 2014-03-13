package org.evilco.mc.flowerpot.configuration.xmltmp;

import com.evilco.configuration.xml.annotation.Comment;
import com.evilco.configuration.xml.annotation.Property;
import com.evilco.configuration.xml.annotation.PropertyWrapper;
import org.evilco.mc.flowerpot.server.MinecraftServer;
import org.evilco.mc.flowerpot.server.capability.Capability;
import org.evilco.mc.flowerpot.server.capability.CapabilityKey;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class XmlServer extends MinecraftServer {

	/**
	 * Stores the alias hostname.
	 */
	@Comment ("Specifies the server forced host.")
	@PropertyWrapper ("alias")
	@Property ("hostname")
	public String aliasHostname = null;

	/**
	 * Stores the alias port.
	 */
	@Comment ("Specifies the server forced port.")
	@PropertyWrapper ("alias")
	@Property ("port")
	public Short aliasPort = null;

	/**
	 * Stores all associated capabilities.
	 */
	@Comment ("Specifies the server capabilities.")
	@PropertyWrapper ("capabilities")
	@Property ("capability")
	public Map<String, Capability<?>> capabilityMap = new HashMap<> ();

	/**
	 * Stores the server hostname.
	 */
	@Comment ("Specifies the server hostname.")
	@Property ("hostname")
	public String hostname = null;

	/**
	 * Stores the server port.
	 */
	@Comment ("Specifies the server port.")
	@Property ("port")
	public Integer port = 25565;

	/**
	 * Serialization constructor.
	 * @fixme Java cannot access any protected or private fields right now
	 */
	public XmlServer () { }

	/**
	 * Constructs a new server.
	 * @param hostname
	 * @param port
	 * @param aliasHostname
	 * @param aliasPort
	 */
	public XmlServer (String hostname, short port, String aliasHostname, short aliasPort) {
		this.hostname = hostname;
		// this.port = port;
		this.aliasHostname = aliasHostname;
		this.aliasPort = aliasPort;

		// add default capabilities
		this.capabilityMap.put (MinecraftServer.CAPABILITY_PROTOCOL.toString (), new Capability<> (4));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCapability (CapabilityKey<?> capabilityKey, Capability<?> capability) {
		if (this.hasCapability (capabilityKey)) throw new InvalidParameterException ("The supplied capability is already registered with this server.");
		this.capabilityMap.put (capabilityKey.toString (), capability);
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
		// return this.port;
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Capability<?> getCapability (CapabilityKey<?> capability) {
		return this.capabilityMap.get (capability.toString ());
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
