package ilarkesto.persistence;

public class UniqueFieldConstraintException extends RuntimeException {

	public UniqueFieldConstraintException(AEntity entity, String field, Object value) {
		super("\"" + value + "\" already exists.");
	}

}
