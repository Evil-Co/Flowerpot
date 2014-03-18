package com.evilco.flowerpot.proxy.configuration.xml;

import com.evilco.configuration.xml.annotation.InnerType;
import com.evilco.flowerpot.proxy.server.MinecraftServer;
import com.evilco.flowerpot.proxy.server.ServerList;

import java.util.Collection;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@InnerType (XmlServer.class)
public class XmlServerList extends ServerList {

	/**
	 * Constructs a new instance of type XmlServerList.
	 */
	public XmlServerList () {
		super ();
	}

	/**
	 * Constructs a new instance of type XmlServerList.
	 * @param c
	 */
	public XmlServerList (Collection<? extends MinecraftServer> c) {
		super (c);
	}
}
