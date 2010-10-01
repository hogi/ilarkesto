package ilarkesto.auth;

public class Auth {

	public static boolean isVisible(Object entity, AUser user) {
		if (entity instanceof ViewProtected) return ((ViewProtected) entity).isVisibleFor(user);
		if (entity instanceof Ownable) return ((Ownable) entity).isOwner(user);
		return true;
	}

	public static boolean isEditable(Object entity, AUser user) {
		if (entity instanceof EditProtected) return ((EditProtected) entity).isEditableBy(user);
		return isVisible(entity, user);
	}

	public static boolean isDeletable(Object entity, AUser user) {
		if (entity instanceof DeleteProtected) return ((DeleteProtected) entity).isDeletableBy(user);
		return isEditable(entity, user);
	}

	private Auth() {}

}
