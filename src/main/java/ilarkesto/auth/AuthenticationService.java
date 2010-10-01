package ilarkesto.auth;

public class AuthenticationService<U extends AUser> {

	public U getUserByLoginAndPassword(String userName, String password) {
		if (userName == null) return null;
		if (password == null) return null;
		for (U user : userDao.getEntities()) {
			if (user.getName().equalsIgnoreCase(userName) && user.matchesPassword(password)) { return user; }
		}
		return null;
	}

	// --- dependencies ---

	private AUserDao<U> userDao;

	public void setUserDao(AUserDao<U> userDao) {
		this.userDao = userDao;
	}

}
