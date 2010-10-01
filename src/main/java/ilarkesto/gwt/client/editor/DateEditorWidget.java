package ilarkesto.gwt.client.editor;

import ilarkesto.gwt.client.AViewEditWidget;
import ilarkesto.gwt.client.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateEditorWidget extends AViewEditWidget {

	private ADateEditorModel model;
	private Label viewer;
	private DatePicker editor;
	private FocusPanel editorWrapper;

	public DateEditorWidget(ADateEditorModel model) {
		super();
		this.model = model;
	}

	@Override
	protected void onViewerUpdate() {
		setViewerValue(model.getValue());
	}

	@Override
	protected void onEditorUpdate() {
		setEditorValue(model.getValue());
		editorWrapper.setFocus(true);
	}

	@Override
	protected void onEditorSubmit() {
		model.changeValue(getEditorValue());
	}

	@Override
	protected final Widget onViewerInitialization() {
		viewer = new Label();
		return viewer;
	}

	@Override
	protected final Widget onEditorInitialization() {
		editor = new DatePicker();
		editor.addValueChangeHandler(new DateChangeHandler());

		editorWrapper = new FocusPanel();
		editorWrapper.addKeyPressHandler(new CancelKeyPressHandler());
		editorWrapper.add(editor);
		editorWrapper.setFocus(true);

		return editorWrapper;
	}

	public final void setViewerValue(Date value) {
		viewer.setText(value == null ? "." : value.toString());
	}

	public final void setEditorValue(Date value) {
		java.util.Date javaDate = value == null ? null : value.toJavaDate();
		editor.setValue(javaDate);
		if (javaDate != null) editor.setCurrentMonth(javaDate);
	}

	public final Date getEditorValue() {
		java.util.Date javaDate = editor.getValue();
		return javaDate == null ? null : new Date(javaDate);
	}

	@Override
	public boolean isEditable() {
		return model.isEditable();
	}

	@Override
	public String getTooltip() {
		return model.getTooltip();
	}

	@Override
	public String getId() {
		return model.getId();
	}

	private class DateChangeHandler implements ValueChangeHandler<java.util.Date> {

		@Override
		public void onValueChange(ValueChangeEvent<java.util.Date> event) {
			submitEditor();
		}

	}

}
