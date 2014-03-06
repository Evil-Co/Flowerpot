package org.evilco.mc.flowerpot.protocol.packet.event;

import io.netty.channel.Channel;
import org.evilco.mc.flowerpot.protocol.packet.AbstractPacket;
import org.evilco.mc.flowerpot.protocol.packet.event.annotation.PacketHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class RegisteredPacketHandler {

	/**
	 * Stores the handler object.
	 */
	protected final Object instance;

	/**
	 * Stores the handler metadata.
	 */
	protected final PacketHandler metadata;

	/**
	 * Stores the handler method.
	 */
	protected final Method method;

	/**
	 * Stores the packet type.
	 */
	protected final Class<? extends AbstractPacket> packetType;

	/**
	 * Constructs a new RegisteredPacketHandler.
	 * @param instance
	 * @param method
	 */
	public RegisteredPacketHandler (Object instance, Method method, Class<? extends AbstractPacket> packetType) {
		this.instance = instance;
		this.method = method;
		this.metadata = method.getAnnotation (PacketHandler.class);
		this.packetType = packetType;

		this.method.setAccessible (true);
	}

	/**
	 * Executes the handler.
	 * @param packet
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void execute (Channel channel, AbstractPacket packet) throws IllegalAccessException, InvocationTargetException {
		this.method.invoke (this.instance, channel, packet);
	}

	/**
	 * Returns the packet class handled by this handler.
	 * @return
	 */
	public Class<? extends AbstractPacket> getPacketClass () {
		return this.packetType;
	}

	/**
	 * Returns the handler priority.
	 * @return
	 */
	public PacketHandlerPriority getPriority () {
		return this.metadata.priority ();
	}

	/**
	 * Returns all expected sides.
	 * @return
	 */
	public PacketHandlerSide[] getSide () {
		return this.metadata.side ();
	}
}
