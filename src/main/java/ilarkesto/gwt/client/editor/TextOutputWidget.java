package ilarkesto.gwt.client.editor;

import ilarkesto.core.base.Str;
import ilarkesto.gwt.client.AViewEditWidget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TextOutputWidget extends AViewEditWidget {

	private Label viewer;
	private AEditorModel model;

	public TextOutputWidget(AEditorModel model) {
		super();
		this.model = model;
	}

	@Override
	protected final Widget onViewerInitialization() {
		viewer = new Label();
		return viewer;
	}

	@Override
	protected final Widget onEditorInitialization() {
		throw new IllegalStateException("no editor");
	}

	@Override
	protected void onViewerUpdate() {
		Object value = model.getValue();
		setViewerText(value == null ? null : String.valueOf(value));
	}

	@Override
	protected void onEditorUpdate() {}

	@Override
	protected void onEditorSubmit() {}

	public final void setViewerText(String text) {
		if (Str.isBlank(text)) text = ".";
		viewer.setText(text);
	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	protected void focusEditor() {}

	@Override
	public String getTooltip() {
		return model.getTooltip();
	}

	@Override
	public String getId() {
		return model.getId();
	}

}
