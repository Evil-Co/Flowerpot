package org.evilco.mc.flowerpot.protocol.packet.event;

import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.protocol.packet.AbstractPacket;
import org.evilco.mc.flowerpot.protocol.packet.event.annotation.PacketHandler;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PacketManager {

	/**
	 * Stores the internal logging instance.
	 */
	protected final Logger logger = LogManager.getFormatterLogger (PacketManager.class);

	/**
	 * Stores all registered packet listeners.
	 */
	protected Map<Class<? extends AbstractPacket>, List<RegisteredPacketHandler>> packetMap = new HashMap<> ();

	/**
	 * Registers a handler instance.
	 * @param instance
	 */
	public void registerHandler (Object instance) {
		for (Method method : instance.getClass ().getMethods ()) {
			if (!method.isAnnotationPresent (PacketHandler.class)) continue;
			this.registerHandler (instance, method);
		}
	}

	/**
	 * Registers a handler instance.
	 * @param instance
	 * @param method
	 */
	public void registerHandler (Object instance, Method method) {
		// log
		logger.debug ("Registering handler method %s of class %s.", method.getName (), instance.getClass ().getName ());

		// verify parameters
		List<Class<?>> argumentList = Arrays.asList (method.getParameterTypes ());

		Class<? extends AbstractPacket> packetType = null;

		try {
			argumentList.get (0).asSubclass (Channel.class);
			packetType = argumentList.get (1).asSubclass (AbstractPacket.class);
		} catch (Exception ex) {
			throw new IllegalArgumentException ("Method argument types do not match the required pattern of Channel, ? extends AbstractPacket", ex);
		}

		// create registered instance
		RegisteredPacketHandler packetHandler = new RegisteredPacketHandler (instance, method, packetType);

		// create list if needed
		List<RegisteredPacketHandler> handlerList = (this.packetMap.containsKey (packetType) ? this.packetMap.get (packetType) : new ArrayList<RegisteredPacketHandler> ());

		// add element based on priority
		for (int i = 0; i < handlerList.size (); i++) {
			if (handlerList.get (i).getPriority ().value < packetHandler.getPriority ().value) {
				handlerList.add (i, packetHandler);
				break;
			}
		}

		// append to empty list
		if (handlerList.size () == 0) handlerList.add (packetHandler);

		// save list
		this.packetMap.put (packetType, handlerList);
	}

	/**
	 * Handles a packet.
	 * @param packet
	 * @param side
	 * @throws Exception
	 */
	public void handlePacket (Channel channel, AbstractPacket packet, PacketHandlerSide side) throws Exception {
		// check handlers
		if (!this.packetMap.containsKey (packet.getClass ())) return;

		// execute handlers
		for (RegisteredPacketHandler packetHandler : this.packetMap.get (packet.getClass ())) {
			for (PacketHandlerSide handlerSide : packetHandler.getSide ()) {
				if (handlerSide.equals (side)) packetHandler.execute (channel, packet);
			}
		}
	}
}
