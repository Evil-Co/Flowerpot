package com.evilco.flowerpot.api.network.packet.exception;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class BadPacketException extends PacketException {

	/**
	 * Constructs a new BadPacketException.
	 */
	public BadPacketException () {
		super ();
	}

	/**
	 * Constructs a new BadPacketException.
	 * @param message
	 */
	public BadPacketException (String message) {
		super (message);
	}

	/**
	 * Constructs a new BadPacketException.
	 * @param message
	 * @param cause
	 */
	public BadPacketException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new BadPacketException.
	 * @param cause
	 */
	public BadPacketException (Throwable cause) {
		super (cause);
	}

	/**
	 * Constructs a new BadPacketException.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	protected BadPacketException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super (message, cause, enableSuppression, writableStackTrace);
	}
}
