package ilarkesto.ui.action;

import ilarkesto.form.Form;
import ilarkesto.form.TextFormField;
import ilarkesto.form.validator.Validator;

public class InputAction extends AAction {

	private TextFormField inputField;

	@Override
	protected void performAction() {
		setAutoShowInfoDone(false);
		Form form = autowire(new Form());
		form.setStringKeyPrefix(getStringKeyPrefix());
		inputField = form.addText("input");
		inputField.setRequired(true);
		if (validator != null) inputField.setValidator(validator);
		form.addSubmitButton("ok");
		form.addAbortSubmitButton();
		showFormDialog(form);
	}

	public String getInputString() {
		return inputField.getValueAsString();
	}

	@Override
	protected void assertPermissions() {}

	// --- dependencies ---

	private Validator validator;

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
