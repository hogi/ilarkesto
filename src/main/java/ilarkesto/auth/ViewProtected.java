package ilarkesto.auth;

public interface ViewProtected<U extends AUser> {

	boolean isVisibleFor(U user);

}
