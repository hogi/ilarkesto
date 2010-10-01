package ilarkesto.gwt.client.editor;

import ilarkesto.core.base.Str;
import ilarkesto.gwt.client.Gwt;

public abstract class AEditorModel<T> {

	public abstract T getValue();

	public abstract void setValue(T value);

	protected void onChangeValue(T oldValue, T newValue) {}

	public boolean isEditable() {
		return true;
	}

	public String getTooltip() {
		return null;
	}

	public void changeValue(T newValue) {
		T oldValue = getValue();
		if (Gwt.equals(oldValue, newValue)) return;
		onChangeValue(oldValue, newValue);
		setValue(newValue);
	}

	public String getId() {
		return Str.getSimpleName(getClass()).replace('$', '_');
	}

}
