package ilarkesto.base;

public class MissingDependencyException extends NullPointerException {

    public MissingDependencyException(String name) {
        super(name);
    }

}
