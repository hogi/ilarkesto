package ilarkesto.gwt.client;

public class EntityDoesNotExistException extends RuntimeException {

	public EntityDoesNotExistException(String entityId) {
		super("Entity does not exist: " + entityId);
	}
}
