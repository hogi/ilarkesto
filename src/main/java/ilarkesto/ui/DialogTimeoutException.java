package ilarkesto.ui;

public class DialogTimeoutException extends RuntimeException {

	public DialogTimeoutException(String dialogLabel) {
		super("Dialog timeout: " + dialogLabel);
	}

}
