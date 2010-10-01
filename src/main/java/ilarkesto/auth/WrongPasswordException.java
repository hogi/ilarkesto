package ilarkesto.auth;

import ilarkesto.base.PermissionDeniedException;

public class WrongPasswordException extends PermissionDeniedException {

	public WrongPasswordException() {
		super("Wrong password.");
	}

}
