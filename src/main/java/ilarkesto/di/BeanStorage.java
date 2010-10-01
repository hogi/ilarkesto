package ilarkesto.di;

import java.util.Map;

/**
 * A write-only container for objects/beans.
 * 
 * @author wko
 */
public interface BeanStorage<B extends Object> {

	public BeanStorage put(String beanName, B bean);

	public BeanStorage putAll(Map<String, ? extends B> beans);

}
