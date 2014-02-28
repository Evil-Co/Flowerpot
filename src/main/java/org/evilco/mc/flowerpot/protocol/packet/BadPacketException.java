package org.evilco.mc.flowerpot.protocol.packet;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class BadPacketException extends PacketException {

	public BadPacketException () {
		super ();
	}

	public BadPacketException (String message) {
		super (message);
	}

	public BadPacketException (String message, Throwable cause) {
		super (message, cause);
	}

	public BadPacketException (Throwable cause) {
		super (cause);
	}

	protected BadPacketException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super (message, cause, enableSuppression, writableStackTrace);
	}
}
