package ilarkesto.auth;

public interface DeleteProtected<U extends AUser> {

	boolean isDeletableBy(U user);

}
