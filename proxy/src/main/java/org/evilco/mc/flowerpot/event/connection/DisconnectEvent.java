package org.evilco.mc.flowerpot.event.connection;

import com.evilco.event.IEvent;
import org.evilco.mc.flowerpot.user.User;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DisconnectEvent implements IEvent {

	/**
	 * Stores the disconnecting user.
	 */
	protected final User user;

	/**
	 * Constructs a new DisconnectEvent.
	 * @param user
	 */
	public DisconnectEvent (User user) {
		this.user = user;
	}

	/**
	 * Returns the disconnecting user.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}
}
