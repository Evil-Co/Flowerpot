package org.evilco.mc.flowerpot.protocol.packet.data;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum HandshakeState {
	STATUS (1),
	LOGIN (2);

	/**
	 * Stores a reverse lookup map.
	 */
	protected static final Map<Integer, HandshakeState> valueMap;

	/**
	 * Static initialization
	 */
	static {
		valueMap = Maps.uniqueIndex (ImmutableList.copyOf (values ()), new Function<HandshakeState, Integer> () {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Integer apply (HandshakeState handshakeState) {
				return handshakeState.value;
			}
		});
	}

	/**
	 * Stores the type value.
	 */
	public final int value;

	/**
	 * Constructs a new HandshakeState.
	 * @param value
	 */
	private HandshakeState (int value) {
		this.value = value;
	}

	/**
	 * Returns a handshake type based by it's integer value.
	 * @param value
	 * @return
	 */
	public static HandshakeState valueOf (int value) {
		return valueMap.get (value);
	}
}
