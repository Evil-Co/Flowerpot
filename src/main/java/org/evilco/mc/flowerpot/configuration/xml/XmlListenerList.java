package org.evilco.mc.flowerpot.configuration.xml;

import com.evilco.configuration.xml.annotation.InnerType;
import org.evilco.mc.flowerpot.server.listener.ListenerList;
import org.evilco.mc.flowerpot.server.listener.ServerListener;

import java.util.Collection;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@InnerType (XMLServerListener.class)
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
