package ilarkesto.di;

import ilarkesto.base.BeanMap;

import java.util.HashSet;
import java.util.Set;

/**
 * Bean provider which uses a bean/object as the source for beans. Each getter in the bean acts as a bean
 * provided by this bean provider.
 * 
 * @author wko
 */
public class ReflectionBeanProvider<T> extends ABeanProvider {

	private BeanMap<T> beanMap;

	public ReflectionBeanProvider(T bean) {
		beanMap = new BeanMap<T>(bean);
	}

	public T getBean() {
		return beanMap.getBean();
	}

	public final Set<String> beanNames() {
		Set<String> result = new HashSet<String>(beanMap.keySet());
		result.remove("class");
		return result;
	}

	public final boolean containsBean(String beanName) {
		return beanMap.containsKey(beanName);
	}

	public final Object getBean(String beanName) {
		return beanMap.get(beanName);
	}

	public final Class getBeanType(String beanName) {
		return beanMap.getType(beanName);
	}

	@Override
	public String toString() {
		return beanMap.getBean().toString();
		// return "ReflectionBeanProvider: " + Utl.toStringWithType(beanMap.getBean());
	}

}
