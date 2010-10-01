package ilarkesto.gwt.client.editor;

import ilarkesto.gwt.client.ADropdownViewEditWidget;
import ilarkesto.gwt.client.LabelProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DropdownEditorWidget<T> extends ADropdownViewEditWidget {

	private AOptionEditorModel<T> model;
	private LabelProvider<T> labelProvider;
	private List<T> options;

	public DropdownEditorWidget(AOptionEditorModel<T> editor, LabelProvider<T> labelProvider) {
		super();
		this.model = editor;
		this.labelProvider = labelProvider;
	}

	@Override
	protected void onViewerUpdate() {
		String label = labelProvider.getLabel(model.getValue());
		setViewerText(label);
	}

	@Override
	protected void onEditorUpdate() {
		options = model.getOptions();
		Map<String, String> optionsMap = new LinkedHashMap<String, String>();
		int index = 0;
		for (T option : options) {
			optionsMap.put(String.valueOf(index), labelProvider.getLabel(option));
			index++;
		}
		setOptions(optionsMap);
	}

	@Override
	protected void onEditorSubmit() {
		String selected = getSelectedOption();
		int index = Integer.parseInt(selected);
		model.changeValue(options.get(index));
		// TODO catch exceptions
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
