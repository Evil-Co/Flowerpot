package com.evilco.flowerpot.proxy.configuration.xml;

import com.evilco.configuration.xml.annotation.InnerType;
import com.evilco.flowerpot.proxy.server.listener.ListenerList;
import com.evilco.flowerpot.proxy.server.listener.ServerListener;

import java.util.Collection;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@InnerType (XmlServerListener.class)
public class XmlListenerList extends ListenerList {

	/**
	 * Constructs a new instance of type XmlListenerList.
	 */
	public XmlListenerList () {
		super ();
	}

	/**
	 * Constructs a new instance of type XmlListenerList.
	 * @param c
	 */
	public XmlListenerList (Collection<? extends ServerListener> c) {
		super (c);
	}
}
