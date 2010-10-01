package ilarkesto.gwt.client;

import ilarkesto.core.base.Str;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;


public class TooltipListener extends MouseListenerAdapter {

	private TooltipPopup tooltip;
	private HTML text;
	private boolean empty;
	private int offsetX = 10;
	private int offsetY = 35;

	public TooltipListener(HTML text) {
		this.text = text;
		this.empty = text == null || Str.isBlank(text.getHTML());
	}

	@Override
	public void onMouseEnter(Widget sender) {
		if (empty) return;
		if (tooltip != null) tooltip.hide();
		tooltip = new TooltipPopup(sender, offsetX, offsetY, text, false);
		tooltip.show();
	}

	@Override
	public void onMouseLeave(Widget sender) {
		if (tooltip != null) tooltip.hide();
	}

	@Override
	public void onMouseMove(Widget sender, int x, int y) {
		if (tooltip != null && tooltip.isActive()) {
			tooltip.hide();
		}
	}

}

