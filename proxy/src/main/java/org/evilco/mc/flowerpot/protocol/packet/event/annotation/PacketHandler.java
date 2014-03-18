package org.evilco.mc.flowerpot.protocol.packet.event.annotation;

import org.evilco.mc.flowerpot.protocol.packet.event.PacketHandlerPriority;
import org.evilco.mc.flowerpot.protocol.packet.event.PacketHandlerSide;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
public @interface PacketHandler {

	/**
	 * Defines the listener priority.
	 * @return
	 */
	public PacketHandlerPriority priority () default PacketHandlerPriority.NORMAL;

	/**
	 * Defines the listener side.
	 * @return
	 */
	public PacketHandlerSide[] side () default { PacketHandlerSide.CLIENT_TO_PROXY };
}