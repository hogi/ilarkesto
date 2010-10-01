package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ButtonWidget extends AWidget {

	private Button button;
	private AAction action;
	private HTML tooltip;

	public ButtonWidget(AAction action) {
		this.action = action;
	}

	@Override
	protected Widget onInitialization() {
		button = new Button(action.getLabel(), action);
		tooltip = Gwt.addHtmlTooltip(button, "");
		return button;
	}

	@Override
	protected void onUpdate() {
		button.getElement().setId("button_" + action.getId());
		button.setText(action.getLabel());
		button.setEnabled(action.isPermitted() && action.isExecutable());
		tooltip.setHTML(action.getTooltip());
	}

	@Override
	public String toString() {
		return "ButtonWidget(" + action + ")";
	}

}
