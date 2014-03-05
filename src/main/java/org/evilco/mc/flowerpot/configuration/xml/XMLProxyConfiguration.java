package org.evilco.mc.flowerpot.configuration.xml;

import org.apache.commons.io.IOUtils;
import org.evilco.mc.flowerpot.FlowerpotServer;
import org.evilco.mc.flowerpot.configuration.IProxyConfiguration;
import org.evilco.mc.flowerpot.server.MinecraftServer;
import org.evilco.mc.flowerpot.server.ServerList;
import org.evilco.mc.flowerpot.server.capability.Capability;
import org.evilco.mc.flowerpot.server.listener.ListenerList;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@XmlRootElement (name = "configuration", namespace = XMLProxyConfiguration.NAMESPACE)
public class XMLProxyConfiguration implements IProxyConfiguration {

	/**
	 * Defines the document namespace.
	 */
	public static final String NAMESPACE = "http://www.flowerpotmc.org/2014/configuration";

	/**
	 * Stores the listener list.
	 */
	protected ListenerList listenerList = new ListenerList (Arrays.asList (new XMLServerListener[] {
		new XMLServerListener ("0.0.0.0", ((short) 25565), true, false)
	}));

	/**
	 * Stores the server list.
	 */
	protected ServerList serverList = new ServerList (Arrays.asList (new XMLServer[] {
		new XMLServer ("localhost", ((short) 25570), "*", ((short) -1))
	}));

	/**
	 * Defines the connection timeout.
	 */
	protected int timeout = 300000;

	/**
	 * Constructs a new empty XMLProxyConfiguration.
	 */
	public XMLProxyConfiguration () {
		this.serverList.get (0).addCapability (MinecraftServer.CAPABILITY_FALLBACK, new Capability<Void> (null));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListenerList getListenerList () {
		return this.listenerList;
	}

	@Override
	public int getProtocolVersion () {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getServerIcon () {
		try {
			return IOUtils.toByteArray (new FileInputStream (new File ("server-icon.png")));
		} catch (Exception ex) {
			try {
				return IOUtils.toByteArray (FlowerpotServer.class.getResourceAsStream ("/defaults/server-icon.png"));
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServerList getServerList () {
		return this.serverList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTimeout () {
		return this.timeout;
	}
}
