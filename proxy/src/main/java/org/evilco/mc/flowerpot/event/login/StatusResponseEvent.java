package org.evilco.mc.flowerpot.event.login;

import com.evilco.event.IEvent;
import org.evilco.mc.flowerpot.protocol.packet.data.StatusResponse;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class StatusResponseEvent implements IEvent {

	/**
	 * Stores the response packet.
	 */
	protected StatusResponse response = null;

	/**
	 * Constructs a new StatusResponseEvent.
	 * @param response
	 */
	public StatusResponseEvent (StatusResponse response) {
		this.response = response;
	}

	/**
	 * Returns the status response.
	 * @return
	 */
	public StatusResponse getResponse () {
		return this.response;
	}

	/**
	 * Sets a new response.
	 * @param response
	 */
	public void setResponse (StatusResponse response) {
		this.response = response;
	}
}
