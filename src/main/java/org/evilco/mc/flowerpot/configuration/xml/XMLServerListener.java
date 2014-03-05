package org.evilco.mc.flowerpot.configuration.xml;

import org.evilco.mc.flowerpot.server.listener.ServerListener;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class XMLServerListener extends ServerListener {

	/**
	 * Indicates whether the proxy is enabled.
	 */
	@XmlAttribute (name = "proxyEnabled", namespace = XMLProxyConfiguration.NAMESPACE)
	protected boolean proxyEnabled = false;

	/**
	 * Indicates whether server query is enabled.
	 */
	@XmlAttribute (name = "queryEnabled", namespace = XMLProxyConfiguration.NAMESPACE)
	protected boolean queryEnabled = false;

	/**
	 * Stores the listener hostname.
	 */
	@XmlValue
	protected String listenerHostname = "0.0.0.0";

	/**
	 * Stores the listener port.
	 */
	@XmlAttribute (name = "port", namespace = XMLProxyConfiguration.NAMESPACE)
	protected short listenerPort = 25565;

	/**
	 * Constructs a new XMLServerListener.
	 * @param hostname
	 * @param port
	 * @param isProxyEnabled
	 * @param isQueryEnabled
	 */
	public XMLServerListener (String hostname, short port, boolean isProxyEnabled, boolean isQueryEnabled) {
		this.listenerHostname = hostname;
		this.listenerPort = port;
		this.proxyEnabled = isProxyEnabled;
		this.queryEnabled = isQueryEnabled;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isQueryEnabled () {
		return this.queryEnabled;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isProxyEnabled () {
		return this.proxyEnabled;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public short getListenerPort () {
		return this.listenerPort;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getListenerHostname () {
		return this.listenerHostname;
	}
}
