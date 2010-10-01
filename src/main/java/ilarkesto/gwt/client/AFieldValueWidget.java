package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AFieldValueWidget extends AWidget {

	private SimplePanel content;
	private HTML viewer;

	@Override
	protected final Widget onInitialization() {
		viewer = new HTML();
		viewer.setStyleName("AFieldValueWidget");
		content = new SimplePanel();
		return content;
	}

	public void setContent(Widget content) {
		this.content.setWidget(content);
	}

	public final void setText(Object text) {
		String s = text == null ? null : text.toString();
		viewer.setText(s);
		content.setWidget(viewer);
	}

	public final void setHtml(String html) {
		viewer.setHTML(html);
		content.setWidget(viewer);
	}

	public void setHours(int hours) {
		setText(Gwt.formatHours(hours));
	}

}
