package ilarkesto.auth;

import ilarkesto.persistence.ADao;

public abstract class AUserDao<U extends AUser> extends ADao<U> {

	public abstract AUser postUser(String name, String password);

	public abstract AUser getUserByName(String name);

}
