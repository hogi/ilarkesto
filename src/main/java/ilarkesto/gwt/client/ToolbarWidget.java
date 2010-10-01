package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.Widget;

public class ToolbarWidget extends AWidget {

	private FloatingFlowPanel panel;

	public ToolbarWidget() {}

	@Override
	protected Widget onInitialization() {
		panel = new FloatingFlowPanel();
		return Gwt.createDiv("ToolbarWidget", panel);
	}

	public void clear() {
		if (panel != null) panel.clear();
	}

	public <W extends Widget> W add(W widget) {
		initialize();
		panel.add(widget);
		if (isInitialized()) update();
		return widget;
	}

	public <W extends Widget> W insert(W widget, int index) {
		initialize();
		panel.insert(widget, index);
		if (isInitialized()) update();
		return widget;
	}

	public ButtonWidget addButton(AAction action) {
		return add(new ButtonWidget(action));
	}

	public HyperlinkWidget addHyperlink(AAction action) {
		return add(new HyperlinkWidget(action));
	}

	public boolean isEmpty() {
		initialize();
		return panel.isEmpty();
	}

}
