package org.evilco.mc.flowerpot.configuration.xml;

import com.evilco.configuration.xml.annotation.Property;
import org.evilco.mc.flowerpot.server.capability.DefaultCapability;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class XmlCapability<T> extends DefaultCapability<T> {

	/**
	 * Stores the capability value (if any).
	 */
	@Property ("value")
	public T value;

	/**
	 * Serialization Constructor.
	 */
	public XmlCapability () {
		super (null);
	}

	/**
	 * Constructs a new XmlCapability.
	 * @param value
	 */
	public XmlCapability (T value) {
		super (value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set (T value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get () {
		return this.value;
	}
}
