package ilarkesto.auth;

public interface EditProtected<U extends AUser> {

	boolean isEditableBy(U user);

}
