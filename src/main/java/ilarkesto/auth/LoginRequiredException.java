package ilarkesto.auth;

import ilarkesto.base.PermissionDeniedException;

public class LoginRequiredException extends PermissionDeniedException {

	public LoginRequiredException() {
		super("Login erforderlich.");
	}

}
