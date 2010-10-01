package ilarkesto.scope;

public class InitializationFaildException extends RuntimeException {

	public InitializationFaildException(Object component, String methodName, Throwable cause) {
		super("Component initialization failed: " + component.getClass().getSimpleName() + "." + methodName + "()",
				cause);
	}
}
