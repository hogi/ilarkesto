package ilarkesto.di;

import java.util.Map;
import java.util.Set;

/**
 * Provides beans and performs autowireing.
 * 
 * @author wko
 */
public interface BeanProvider {

    /**
     * Provides a set of all existing bean names.
     */
    public Set<String> beanNames();

    /**
     * Gets a bean by name.
     */
    public <T> Object getBean(String beanName);

    /**
     * Gets a beans type by the beans name.
     */
    public Class getBeanType(String beanName);

    /**
     * Gets all beans by their type. All beans instanceof the given type are returned.
     */
    public <T> Set<T> getBeansByType(Class<T> type);

    /**
     * Gets all existing beans.
     */
    public Map<String, Object> getAllBeans();

    // --- autowireing ---

    /**
     * Autowire the given class.
     * 
     * @see Autowire#autowireClass(Class, IBeanProvider, IObjectStringMapper)
     */
    public <T> Class<T> autowireClass(Class<T> type);

    /**
     * Autowire the given bean.
     * 
     * @see Autowire#autowire(Object, IBeanProvider, IObjectStringMapper)
     */
    public <T> T autowire(T bean);

}
