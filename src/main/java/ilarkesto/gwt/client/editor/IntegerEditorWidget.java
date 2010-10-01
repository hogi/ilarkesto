package ilarkesto.gwt.client.editor;

import ilarkesto.gwt.client.AIntegerViewEditWidget;

public class IntegerEditorWidget extends AIntegerViewEditWidget {

	private AIntegerEditorModel model;

	public IntegerEditorWidget(AIntegerEditorModel editor) {
		super();
		this.model = editor;
	}

	@Override
	protected void onIntegerViewerUpdate() {
		setViewerValue(model.getValue());
	}

	@Override
	protected void onMinusClicked() {
		Integer value = model.getValue();
		if (value == null || value <= model.getMin()) return;
		model.decrement();
	}

	@Override
	protected void onPlusClicked() {
		Integer value = model.getValue();
		if (value != null && value >= model.getMax()) return;
		if (value == null) model.setValue(0);
		model.increment();
	}

	@Override
	protected void onEditorSubmit() {
		model.changeValue(getEditorValue());
	}

	@Override
	protected void onEditorUpdate() {
		setEditorValue(model.getValue());
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

}
