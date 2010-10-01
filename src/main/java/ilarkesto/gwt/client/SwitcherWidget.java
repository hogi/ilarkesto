package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SwitcherWidget extends AWidget {

	private Widget currentWidget;
	private boolean height100;

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
		return widget;
	}

	public boolean isShowing(Widget widget) {
		return currentWidget == widget;
	}

	public Widget getCurrentWidget() {
		return currentWidget;
	}

	@Override
	public String toString() {
		return "SwitcherWidget(" + Gwt.toString(currentWidget) + ")";
	}

}
