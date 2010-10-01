package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public abstract class ATextWidget extends AWidget {

	private HTML viewer;

	@Override
	protected final Widget onInitialization() {
		viewer = new HTML();
		return viewer;
	}

	public final void setText(Object text) {
		String s = text == null ? null : text.toString();
		viewer.setText(s);
	}

	public final void setHtml(String html) {
		viewer.setHTML(html);
	}

	public void setHours(int hours) {
		setText(Gwt.formatHours(hours));
	}

}
