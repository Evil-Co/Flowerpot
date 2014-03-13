package org.evilco.mc.flowerpot.configuration.xmltmp;

import com.evilco.configuration.xml.annotation.Comment;
import com.evilco.configuration.xml.annotation.Property;
import com.evilco.configuration.xml.annotation.PropertyWrapper;
import org.evilco.mc.flowerpot.server.listener.ServerListener;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class XmlServerListener extends ServerListener {

	/**
	 * Indicates whether the proxy is enabled.
	 */
	@Comment ("Defines whether servers are available via this listener.")
	@PropertyWrapper ("protocols")
	@Property ("proxy")
	public Boolean proxyEnabled = false;

	/**
	 * Indicates whether server query is enabled.
	 */
	@Comment ("Defines whether server query is available via this listener.")
	@PropertyWrapper ("protocols")
	@Property ("query")
	public Boolean queryEnabled = false;

	/**
	 * Stores the listener hostname.
	 */
	@Comment ("Specifies the hostname to listen on.")
	@Property ("hostname")
	public String listenerHostname = "0.0.0.0";

	/**
	 * Stores the listener port.
	 */
	@Comment ("Specifies the port to listen on.")
	@Property ("port")
	public Short listenerPort = 25565;

	/**
	 * Serialization Constructor
	 */
	public XmlServerListener () { }

	/**
	 * Constructs a new XmlServerListener.
	 * @param hostname
	 * @param port
	 * @param isProxyEnabled
	 * @param isQueryEnabled
	 */
	public XmlServerListener (String hostname, short port, boolean isProxyEnabled, boolean isQueryEnabled) {
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
