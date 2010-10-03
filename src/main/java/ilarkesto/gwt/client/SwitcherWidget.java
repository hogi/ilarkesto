package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class SwitcherWidget extends AWidget {

	private Widget currentWidget;
	private boolean height100;
	private ScrollPanel scrollPanel;

	public SwitcherWidget(boolean height100) {
		this.height100 = height100;
	}

	@Override
	protected final Widget onInitialization() {
		if (height100) setHeight100();
		return new Label("Empty SwitcherWidget");
	}

	public <W extends Widget> W show(W widget) {
		if (currentWidget == widget) {
			// update();
			return widget;
		}
		currentWidget = widget;
		replaceContent(currentWidget);
		update();
		if (scrollPanel != null) scrollPanel.scrollToTop();
		return widget;
	}

	public boolean isShowing(Widget widget) {
		return currentWidget == widget;
	}

	public Widget getCurrentWidget() {
		return currentWidget;
	}

	public void setScrollPanel(ScrollPanel scrollPanel) {
		this.scrollPanel = scrollPanel;
	}

	@Override
	public String toString() {
		return "SwitcherWidget(" + Gwt.toString(currentWidget) + ")";
	}

}
