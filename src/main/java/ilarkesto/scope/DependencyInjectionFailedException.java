package ilarkesto.scope;

public class DependencyInjectionFailedException extends RuntimeException {

	public DependencyInjectionFailedException(Object component, String fieldName, Object dependency, Throwable cause) {
		super("Component dependency injection failed: " + component.getClass().getSimpleName() + "." + fieldName
				+ " = " + dependency, cause);
	}

}
