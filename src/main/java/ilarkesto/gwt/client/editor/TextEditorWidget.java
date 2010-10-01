package ilarkesto.gwt.client.editor;

import ilarkesto.core.base.Str;
import ilarkesto.gwt.client.AViewEditWidget;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TextEditorWidget extends AViewEditWidget {

	private Label viewer;
	private TextBox editor;
	private ATextEditorModel model;

	public TextEditorWidget(ATextEditorModel model) {
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
		editor = new TextBox();
		editor.setMaxLength(model.getMaxLenght());
		editor.setWidth("97%");
		editor.addFocusListener(new EditorFocusListener());
		editor.addKeyPressHandler(new EditorKeyboardListener());
		return editor;
	}

	@Override
	protected void onViewerUpdate() {
		setViewerText(model.getValue());
	}

	@Override
	protected void onEditorUpdate() {
		setEditorText(model.getValue());
	}

	@Override
	protected void onEditorSubmit() {
		String value = getEditorText();
		// TODO check lenght
		// TODO check format/syntax
		model.changeValue(value);
		// TODO catch exceptions
	}

	public final void setViewerText(String text) {
		if (Str.isBlank(text)) text = ".";
		viewer.setText(text);
	}

	public final void setEditorText(String text) {
		editor.setText(text);
		focusEditor();
		editor.setSelectionRange(0, editor.getText().length());
	}

	@Override
	public boolean isEditable() {
		return model.isEditable();
	}

	@Override
	protected void focusEditor() {
		editor.setFocus(true);
	}

	public final String getEditorText() {
		return editor.getText();
	}

	protected Label getViewer() {
		return viewer;
	}

	public TextEditorWidget switchToEditModeIfNull() {
		if (isEditable() && Str.isBlank(model.getValue())) switchToEditMode();
		return this;
	}

	@Override
	public String getTooltip() {
		return model.getTooltip();
	}

	@Override
	public String getId() {
		return model.getId();
	}

	private class EditorKeyboardListener implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			char keyCode = event.getCharCode();
			if (keyCode == KeyCodes.KEY_ENTER) {
				submitEditor();
			} else if (keyCode == KeyCodes.KEY_ESCAPE) {
				cancelEditor();
			}
		}
	}

	private class EditorFocusListener implements FocusListener {

		public void onFocus(Widget sender) {}

		public void onLostFocus(Widget sender) {
			submitEditor();
		}

	}
}
