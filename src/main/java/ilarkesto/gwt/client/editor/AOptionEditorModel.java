package ilarkesto.gwt.client.editor;

import java.util.List;

public abstract class AOptionEditorModel<T> extends AEditorModel<T> {

	public abstract List<T> getOptions();

	public boolean isMandatory() {
		return false;
	}

}
