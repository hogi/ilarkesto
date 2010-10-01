package ilarkesto.di;

public class BeanDoesNotExisException extends RuntimeException {

	public BeanDoesNotExisException(String beanName) {
		super("Bean does not exist: " + beanName);
	}

}
