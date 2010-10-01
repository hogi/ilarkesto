package ilarkesto.base;

public class PermissionDeniedException extends RuntimeException {

	public PermissionDeniedException(String message) {
		super(message);
	}

	public PermissionDeniedException() {}

}
