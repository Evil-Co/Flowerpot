package com.evilco.flowerpot.api.network.exception;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class NetworkException extends Exception {

	/**
	 * Constructs a new NetworkException.
	 */
	public NetworkException () {
		super ();
	}

	/**
	 * Constructs a new NetworkException.
	 * @param message
	 */
	public NetworkException (String message) {
		super (message);
	}

	/**
	 * Constructs a new NetworkException.
	 * @param message
	 * @param cause
	 */
	public NetworkException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new NetworkException.
	 * @param cause
	 */
	public NetworkException (Throwable cause) {
		super (cause);
	}

	/**
	 * Constructs a new NetworkException.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	protected NetworkException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super (message, cause, enableSuppression, writableStackTrace);
	}
}
