package ilarkesto.ui.swing;

import ilarkesto.auth.LoginData;
import ilarkesto.di.app.AApplicationConfig;
import ilarkesto.properties.APropertiesStore;

public class ASwingApplicationConfig extends AApplicationConfig {

	public ASwingApplicationConfig(APropertiesStore p) {
		super(p);
	}

	public final LoginData getLoginData(String id) {
		String login = p.get("login." + id + ".login");
		String password = p.getCrypted("login." + id + ".password");
		if (login == null && password == null) return null;
		return new LoginData(login, password, true);
	}

	public final void setLoginData(String id, LoginData loginData) {
		p.set("login." + id + ".login", loginData.getLogin());
		p.setCrypted("login." + id + ".password", loginData.getPassword());
	}

	public final void removeLoginData(String id) {
		p.remove("login." + id + ".login");
		p.remove("login." + id + ".password");
	}

}
