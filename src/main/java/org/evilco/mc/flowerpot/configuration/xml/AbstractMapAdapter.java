package org.evilco.mc.flowerpot.configuration.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AbstractMapAdapter<T, E> extends XmlAdapter<List<AbstractMapAdapter.MapElement<T, E>>, Map<T, E>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<T, E> unmarshal (List<MapElement<T, E>> v) throws Exception {
		// create map instance
		Map<T, E> map = new HashMap<> ();

		// decode elements
		for (MapElement<T, E> element : v) {
			map.put (element.key, element.value);
		}

		// return finished map
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MapElement<T, E>> marshal (Map<T, E> v) throws Exception {
		List<MapElement<T, E>> elementList = new ArrayList<> ();

		// encode elements
		for (Map.Entry<T, E> entry : v.entrySet ()) {
			elementList.add (new MapElement<> (entry.getKey (), entry.getValue ()));
		}

		// return finished list
		return elementList;
	}

	/**
	 * Represents an element.
	 * @param <T>
	 * @param <E>
	 */
	public static class MapElement<T, E> {

		/**
		 * Stores the key.
		 */
		@XmlAttribute (name = "key")
		public T key;

		/**
		 * Stores the value.
		 */
		@XmlAttribute
		public E value;

		/**
		 * Constructs a new MapElement.
		 * @param key
		 * @param value
		 */
		public MapElement (T key, E value) {
			this.key = key;
			this.value = value;
		}
	}
}
