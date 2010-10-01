package ilarkesto.core.scope;

public class ComponentAlreadyExistsException extends RuntimeException {

	public ComponentAlreadyExistsException(String componentName, Scope scope) {
		super("Component already exists in scope: " + componentName + " -> " + scope.getName());
	}
}
