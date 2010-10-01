package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.Widget;

public class HyperlinkWidget extends AWidget {

	private HyperlinkWithoutHistory hyperlink;
	private AAction action;

	public HyperlinkWidget(AAction action) {
		this.action = action;
	}

	@Override
	protected Widget onInitialization() {
		hyperlink = new HyperlinkWithoutHistory();
		hyperlink.addStyleName("HyperlinkWidget");
		hyperlink.addClickHandler(action);
		hyperlink.setTitle(action.getTooltip());
		return hyperlink;
	}

	@Override
	protected void onUpdate() {
		hyperlink.getElement().setId("hyperlink_" + action.getId());
		hyperlink.setText(action.getLabel());
		hyperlink.setTitle(action.getTooltip());
		hyperlink.setVisible(action.isExecutable());
	}

	@Override
	public String toString() {
		return "HyperlinkWidget(" + action + ")";
	}

}
