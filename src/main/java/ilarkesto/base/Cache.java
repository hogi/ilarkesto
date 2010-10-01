package ilarkesto.base;

import ilarkesto.core.logging.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple cache, where keys are mapped to value objects. When no value-object is assigned to a key, then a
 * create method is called on the user provided factory.
 */
public class Cache<K, V> {

	private static final Log LOG = Log.get(Cache.class);

	private Map<K, V> cache = new HashMap<K, V>();

	private Factory<K, V> factory;

	public Cache(Factory<K, V> factory) {
		this.factory = factory;
	}

	Cache() {}

	void setFactory(Factory<K, V> factory) {
		this.factory = factory;
	}

	public void clear() {
		cache = new HashMap<K, V>();
	}

	public V get(K key) {
		V value = cache.get(key);
		if (value == null) {
			value = factory.create(key);
			// LOG.debug("Returning new:", key, "->", value);
			cache.put(key, value);
			// } else {
			// LOG.debug("Returning cached:", key, "->", value);
		}
		return value;
	}

	public static interface Factory<K, V> {

		V create(K key);

	}

}
