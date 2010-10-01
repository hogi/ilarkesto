package ilarkesto.ui.swing;

import ilarkesto.auth.LoginData;
import ilarkesto.swing.PanelBuilder;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginComponent extends AComponent {

	private JTextField loginField;
	private JPasswordField passwordField;
	private JCheckBox savePasswordCheckbox;

	// --- dependencies ---

	private ActionListener loginActionListener;
	private LoginData loginData;

	public void setLoginActionListener(ActionListener loginActionListener) {
		this.loginActionListener = loginActionListener;
	}

	public void setInitialLoginData(LoginData loginData) {
		this.loginData = loginData;
	}

	// --- ---

	@Override
	protected void initializeControls() {
		loginField = new JTextField(15);
		if (loginActionListener != null) loginField.addActionListener(loginActionListener);
		passwordField = new JPasswordField(15);
		if (loginActionListener != null) passwordField.addActionListener(loginActionListener);
		savePasswordCheckbox = new JCheckBox();
		savePasswordCheckbox.setSelected(false);
		savePasswordCheckbox.setText(string("savePasswordCheckbox.text"));
	}

	@Override
	protected JComponent createComponent() {
		PanelBuilder fields = new PanelBuilder();
		fields.setDefaultPadding(2, 2, 5, 5);
		fields.addEmpty().setWeightY(1000);
		fields.nl();
		fields.add(string("loginField.label")).setAnchorToEast();
		fields.add(loginField).setFillToBoth();
		fields.nl();
		fields.add(string("passwordField.label")).setAnchorToEast().setWeightY(20);
		fields.add(passwordField).setFillToBoth();
		fields.nl();
		fields.add("");
		fields.add(savePasswordCheckbox).setAnchorToWest();
		fields.nl();
		fields.addEmpty().setWeightY(1000);
		return fields.toPanel();
	}

	@Override
	protected void updateControls() {
		if (loginData != null) {
			loginField.setText(loginData.getLogin());
			passwordField.setText(loginData.getPassword());
			savePasswordCheckbox.setSelected(loginData.isSavePassword());
		}
	}

	public LoginData getLoginData() {
		return new LoginData(loginField.getText(), new String(passwordField.getPassword()), savePasswordCheckbox
				.isSelected());
	}

}
