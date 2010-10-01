package ilarkesto.ui.action;

public class ActionAbortedException extends RuntimeException {

    public ActionAbortedException() {}

    public ActionAbortedException(String message) {
        super(message);
    }

}
