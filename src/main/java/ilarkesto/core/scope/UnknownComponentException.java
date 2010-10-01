package ilarkesto.core.scope;

public class UnknownComponentException extends RuntimeException {

	public UnknownComponentException(String name) {
		super("Unknown component: " + name);
	}

}
