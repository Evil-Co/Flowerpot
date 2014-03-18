package com.evilco.flowerpot.proxy.event.login;

import com.evilco.event.ICancellableEvent;
import com.evilco.event.IEvent;
import com.evilco.flowerpot.proxy.server.MinecraftServer;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PreLoginEvent implements IEvent, ICancellableEvent {

	/**
	 * Indicates whether the event is cancelled.
	 */
	protected boolean isCancelled = false;

	/**
	 * Stores the server to connect to.
	 */
	protected MinecraftServer server = null;

	/**
	 * Constructs a new PreLoginEvent.
	 * @param server
	 */
	public PreLoginEvent (MinecraftServer server) {
		this.server = server;
	}

	/**
	 * Returns the server to connect to.
	 * @return
	 */
	public MinecraftServer getServer () {
		return this.server;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCancelled () {
		return this.isCancelled;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCancelled (boolean b) {
		this.isCancelled = b;
	}

	/**
	 * Sets a new server.
	 * @param server
	 */
	public void setServer (MinecraftServer server) {
		this.server = server;
	}
}
