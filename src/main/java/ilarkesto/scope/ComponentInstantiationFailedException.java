package ilarkesto.scope;

public class ComponentInstantiationFailedException extends RuntimeException {

	public ComponentInstantiationFailedException(String name, Class type, Throwable cause) {
		super("Instantiating component failed: " + name + " -> " + type.getName(), cause);
	}

}
