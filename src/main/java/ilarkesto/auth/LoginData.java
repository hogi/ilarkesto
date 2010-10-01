package ilarkesto.auth;

public class LoginData implements LoginDataProvider {

	private String login;
	private String password;
	private boolean savePassword;

	public LoginData(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public LoginData(String login, String password, boolean savePassword) {
		this(login, password);
		this.savePassword = savePassword;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public boolean isSavePassword() {
		return savePassword;
	}

	@Override
	public LoginData getLoginData() {
		return this;
	}

	@Override
	public String toString() {
		return login + ":" + (password == null ? "null" : "*****") + ":" + savePassword;
	}
}
