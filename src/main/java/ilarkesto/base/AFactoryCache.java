package ilarkesto.base;

public abstract class AFactoryCache<K, V> extends Cache<K, V> implements Cache.Factory<K, V> {

	public AFactoryCache() {
		setFactory(this);
	}

}
