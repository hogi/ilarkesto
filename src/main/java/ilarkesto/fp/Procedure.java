package ilarkesto.fp;

public abstract class Procedure<T> implements Function<T, T> {

    public abstract void exec(T e);

    public T eval(T e) {
        exec(e);
        return e;
    }

}
