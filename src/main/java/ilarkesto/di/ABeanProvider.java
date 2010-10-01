package ilarkesto.di;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Helper for implementing own BeanProviders.
 * 
 * @author wko
 */
public abstract class ABeanProvider implements BeanProvider {

    @SuppressWarnings("unchecked")
    public final <T> Set<T> getBeansByType(Class<T> type) {
        Set<T> result = new HashSet<T>();
        for (String beanName : beanNames()) {
            if (type.isAssignableFrom(getBeanType(beanName))) result.add((T) getBean(beanName));
        }
        return result;
    }

    public final Map<String, Object> getAllBeans() {
        Map<String, Object> result = new HashMap<String, Object>();
        for (String beanName : beanNames()) {
            result.put(beanName, getBean(beanName));
        }
        return result;
    }

    // --- autowireing ---

    public final <T> T autowire(T bean) {
        return Autowire.autowire(bean, this, objectStringMapper);
    }

    public final <T> Class<T> autowireClass(Class<T> type) {
        return Autowire.autowireClass(type, this, objectStringMapper);
    }

    // --- dependencies ---

    protected ObjectStringMapper objectStringMapper;

    public final void setObjectStringMapper(ObjectStringMapper objectStringMapper) {
        this.objectStringMapper = objectStringMapper;
    }
}
