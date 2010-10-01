package ilarkesto.auth;

public interface Ownable<U extends AUser> {

	boolean isOwner(U user);

	void setOwner(U user);

}
