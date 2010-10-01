package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AOutputViewEditWidget extends AViewEditWidget {

	private SimplePanel wrapper;

	public void setViewer(Widget viewer) {
		initialize();
		wrapper.setWidget(viewer);
	}

	@Override
	protected Widget onViewerInitialization() {
		wrapper = new SimplePanel();
		return wrapper;
	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	protected Widget onEditorInitialization() {
		return null;
	}

	@Override
	protected void onEditorSubmit() {}

	@Override
	protected void onEditorUpdate() {}

}
