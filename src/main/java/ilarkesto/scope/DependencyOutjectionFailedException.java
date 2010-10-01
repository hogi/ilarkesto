package ilarkesto.scope;

public class DependencyOutjectionFailedException extends RuntimeException {

	public DependencyOutjectionFailedException(Object component, String fieldName, Throwable cause) {
		super("Component dependency outjection failed: " + component.getClass().getSimpleName() + "." + fieldName,
				cause);
	}

}
