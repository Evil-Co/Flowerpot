package org.evilco.mc.flowerpot.configuration.xmltmp;

import com.evilco.configuration.xml.ConfigurationProcessor;
import com.evilco.configuration.xml.annotation.Comment;
import com.evilco.configuration.xml.annotation.Configuration;
import com.evilco.configuration.xml.annotation.Property;
import com.evilco.configuration.xml.annotation.PropertyWrapper;
import com.evilco.configuration.xml.exception.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.evilco.mc.flowerpot.FlowerpotServer;
import org.evilco.mc.flowerpot.configuration.IProxyConfiguration;
import org.evilco.mc.flowerpot.server.MinecraftServer;
import org.evilco.mc.flowerpot.server.ServerList;
import org.evilco.mc.flowerpot.server.capability.Capability;
import org.evilco.mc.flowerpot.server.listener.ListenerList;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Configuration (namespace = XmlProxyConfiguration.NAMESPACE)
public class XmlProxyConfiguration implements IProxyConfiguration {

	/**
	 * Stores the internal logger instance.
	 */
	protected static final Logger logger = LogManager.getLogger (XmlProxyConfiguration.class);

	/**
	 * Defines the document namespace.
	 */
	public static final String NAMESPACE = "http://www.flowerpotmc.org/2014/configuration";

	/**
	 * Stores the listener list.
	 */
	@Comment ("Specifies listeners for the proxy.")
	@PropertyWrapper ("listeners")
	@Property ("listener")
	public XmlListenerList listenerList = null;

	/**
	 * Stores the server list.
	 */
	@Comment ("Specifies the servers registered with the proxy.")
	@PropertyWrapper ("servers")
	@Property ("server")
	public XmlServerList serverList = null;

	/**
	 * Defines the connection timeout.
	 */
	@Comment ("Defines how long a client is allowed to idle before being timed out.")
	@Property ("timeout")
	public Integer timeout = 300000;

	/**
	 * Constructs a new empty XmlProxyConfiguration.
	 */
	public XmlProxyConfiguration () {
		// create default entries
		this.serverList = new XmlServerList (Arrays.asList (new XmlServer[] {
			new XmlServer ("localhost", ((short) 25570), "*", ((short) -1))
		}));

		this.listenerList = new XmlListenerList (Arrays.asList (new XmlServerListener[] {
			new XmlServerListener ("0.0.0.0", ((short) 25565), true, false)
		}));

		// add fallback capability
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

	/**
	 * Loads a configuration file.
	 * @param file
	 * @return
	 */
	public static XmlProxyConfiguration load (File file) throws ConfigurationException {
		return ConfigurationProcessor.getInstance ().load (file, XmlProxyConfiguration.class);
	}

	/**
	 * Constructs a new instance.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static XmlProxyConfiguration newInstance (File file) throws Exception {
		try {
			return load (file);
		} catch (Exception ex) {
			XmlProxyConfiguration configuration = new XmlProxyConfiguration ();
			configuration.save (file);

			logger.debug ("Could not load the configuration file.", ex);

			return configuration;
		}
	}

	/**
	 * Saves the configuration file.
	 * @param file
	 * @throws Exception
	 */
	public void save (File file) throws ConfigurationException {
		System.out.println (this.serverList);

		ConfigurationProcessor.getInstance ().save (this, file);
	}
}
