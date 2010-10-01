package ilarkesto.di;

import ilarkesto.base.Str;
import ilarkesto.core.logging.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A bean provider which wrapps other bean providers.
 * 
 * @author wko
 */
public final class MultiBeanProvider extends ABeanProvider {

	private static final Log LOG = Log.get(MultiBeanProvider.class);

	private Set<BeanProvider> beanProviders = new HashSet<BeanProvider>();
	private Map<String, BeanProvider> beanToBeanProvider = new HashMap<String, BeanProvider>();

	public synchronized void addBeanProvider(Object object) {
		if (object == null) throw new IllegalArgumentException("object == null");

		// identify object and create beanProvider
		BeanProvider beanProvider;
		if (object instanceof BeanProvider) {
			beanProvider = (BeanProvider) object;
		} else {
			if (object instanceof Map) {
				beanProvider = new BeanContainer((Map<String, Object>) object);
			} else {
				beanProvider = new ReflectionBeanProvider(object);
			}
		}

		// get objectStringMapper from beanProvider
		if (objectStringMapper == null && beanProvider instanceof ABeanProvider)
			objectStringMapper = ((ABeanProvider) beanProvider).objectStringMapper;

		// register beanProvider for its beans
		for (String beanName : beanProvider.beanNames()) {
			if ("beanProvider".equals(beanName)) throw new RuntimeException("Forbidden bean: beanProvider");
			beanToBeanProvider.put(beanName, beanProvider);
		}

		// add beanProvider
		beanProviders.add(beanProvider);
	}

	public Set<String> beanNames() {
		return beanToBeanProvider.keySet();
	}

	public <T> Object getBean(String beanName) {
		BeanProvider provider = beanToBeanProvider.get(beanName);
		if (provider == null) throw new BeanDoesNotExisException(beanName);
		return provider.getBean(beanName);
	}

	public Class getBeanType(String beanName) {
		BeanProvider provider = beanToBeanProvider.get(beanName);
		if (provider == null) throw new BeanDoesNotExisException(beanName);
		return provider.getBeanType(beanName);
	}

	@Override
	public String toString() {
		return "(" + Str.concat(beanProviders, ", ") + ")";
	}

	// --- dependencies ---

}
