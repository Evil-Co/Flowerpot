package org.evilco.mc.flowerpot.event.login;

import com.evilco.event.IEvent;
import org.evilco.mc.flowerpot.server.capability.CapabilityKey;
import org.evilco.mc.flowerpot.server.capability.ICapability;

import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class HandshakeEvent implements IEvent {

	/**
	 * Stores the capability map.
	 */
	protected Map<CapabilityKey<?>, ICapability<?>> capabilityMap;

	/**
	 * Constructs a new HandshakeEvent.
	 * @param capabilityMap
	 */
	public HandshakeEvent (Map<CapabilityKey<?>, ICapability<?>> capabilityMap) {
		this.capabilityMap = capabilityMap;
	}

	/**
	 * Returns the handshake compatibility map.
	 * @return
	 */
	public Map<CapabilityKey<?>, ICapability<?>> getCapabilityMap () {
		return this.capabilityMap;
	}

	/**
	 * Sets a new capability map.
	 * @param capabilityMap
	 */
	public void setCapabilityMap (Map<CapabilityKey<?>, ICapability<?>> capabilityMap) {
		this.capabilityMap = capabilityMap;
	}
}
