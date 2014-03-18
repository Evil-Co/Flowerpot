package com.evilco.flowerpot.proxy.protocol.packet;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class PacketException extends Exception {

	public PacketException () {
		super ();
	}

	public PacketException (String message) {
		super (message);
	}

	public PacketException (String message, Throwable cause) {
		super (message, cause);
	}

	public PacketException (Throwable cause) {
		super (cause);
	}

	protected PacketException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super (message, cause, enableSuppression, writableStackTrace);
	}
}
