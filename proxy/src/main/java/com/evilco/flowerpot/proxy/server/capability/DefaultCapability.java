package com.evilco.flowerpot.proxy.server.capability;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DefaultCapability<T> extends AbstractCapability<T> {

	/**
	 * Stores the capability value.
	 */
	protected T value;

	/**
	 * Constructs a new DefaultCapability.
	 * @param value
	 */
	public DefaultCapability (T value) {
		this.set (value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get () {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set (T value) {
		this.value = value;
	}
}
