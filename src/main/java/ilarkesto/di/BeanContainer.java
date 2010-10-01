package ilarkesto.di;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A storage for beans used for autowireing.
 * 
 * @author wko
 */
public final class BeanContainer extends ABeanProvider implements BeanStorage<Object> {

	private Map<String, Object>	beans;

	public Set<String> beanNames() {
		return beans.keySet();
	}

	public Object getBean(String beanName) {
		return beans.get(beanName);
	}

	public Class getBeanType(String beanName) {
		Object bean = beans.get(beanName);
		if (bean == null) return null;
		return bean.getClass();
	}

	public BeanContainer put(String name, Object bean) {
		beans.put(name, bean);
		return this;
	}

	public BeanContainer putAll(Map<String, ? extends Object> map) {
		beans.putAll(map);
		return this;
	}

	public BeanContainer putAll(BeanProvider beanProvider) {
		for (String bean : beanProvider.beanNames()) {
			beans.put(bean, beanProvider.getBean(bean));
		}
		return this;
	}

	public Map<String, Object> getBeans() {
		return beans;
	}

	// --- dependencies ---

	public BeanContainer(Map<String, Object> beans) {
		this.beans = beans;
	}

	public BeanContainer() {
		this.beans = new HashMap<String, Object>();
	}

}

// $Log: BeanContainer.java,v $
// Revision 1.1 2006/08/25 15:58:37 wko
// *** empty log message ***
//
