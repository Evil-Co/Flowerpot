package com.evilco.flowerpot.api.network.packet.exception;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ExtraDataPacketException extends BadPacketException {

	/**
	 * Constructs a new ExtraDataPacketException.
	 */
	public ExtraDataPacketException () {
		super ();
	}

	/**
	 * Constructs a new ExtraDataPacketException.
	 * @param message
	 */
	public ExtraDataPacketException (String message) {
		super (message);
	}

	/**
	 * Constructs a new ExtraDataPacketException.
	 * @param message
	 * @param cause
	 */
	public ExtraDataPacketException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new ExtraDataPacketException.
	 * @param cause
	 */
	public ExtraDataPacketException (Throwable cause) {
		super (cause);
	}

	/**
	 * Constructs a new ExtraDataPacketException.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	protected ExtraDataPacketException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super (message, cause, enableSuppression, writableStackTrace);
	}
}
