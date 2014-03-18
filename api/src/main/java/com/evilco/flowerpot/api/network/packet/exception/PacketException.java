package com.evilco.flowerpot.api.network.packet.exception;

import com.evilco.flowerpot.api.network.exception.NetworkException;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PacketException extends NetworkException {

	/**
	 * Constructs a new PacketException.
	 */
	public PacketException () {
		super ();
	}

	/**
	 * Constructs a new PacketException.
	 * @param message
	 */
	public PacketException (String message) {
		super (message);
	}

	/**
	 * Constructs a new PacketException.
	 * @param message
	 * @param cause
	 */
	public PacketException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new PacketException.
	 * @param cause
	 */
	public PacketException (Throwable cause) {
		super (cause);
	}

	/**
	 * Constructs a new PacketException.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	protected PacketException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super (message, cause, enableSuppression, writableStackTrace);
	}
}
