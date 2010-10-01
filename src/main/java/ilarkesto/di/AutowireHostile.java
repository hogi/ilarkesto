package ilarkesto.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Setters annotated with this are not called while autowireing.
 * 
 * @see Autowire
 * @see BeanProvider
 * @author wko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutowireHostile {

}
