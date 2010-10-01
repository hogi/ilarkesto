package ilarkesto.auth;

import ilarkesto.persistence.AEntity;

public abstract class AUser extends AEntity {

	public abstract String getName();

	public abstract String getRealName();

	public abstract void setPassword(String value);

	public abstract boolean matchesPassword(String password);

	public abstract boolean isAdmin();

	public abstract String getAutoLoginString();

	public int compareTo(AUser o) {
		return toString().toLowerCase().compareTo(o.toString().toLowerCase());
	}

}
