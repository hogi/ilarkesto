package ilarkesto.gwt.client;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class FullscreenPanel extends SimplePanel {

	public FullscreenPanel() {
		setStyleName("FullscreenPanel");
		updateHeight();
		Window.addResizeHandler(new Autoresizer());
	}

	public FullscreenPanel(Widget content) {
		this();
		setWidget(content);
	}

	public void updateHeight() {
		int height = Window.getClientHeight() - 25;
		setHeight(height + "px");
	}

	@Override
	public String toString() {
		return "FullscreenPanel(" + Gwt.toString(getWidget()) + ")";
	}

	private class Autoresizer implements ResizeHandler {

		public void onResize(ResizeEvent event) {
			updateHeight();
		}
	}
}
