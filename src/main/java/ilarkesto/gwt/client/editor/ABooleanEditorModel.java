package ilarkesto.gwt.client.editor;

public abstract class ABooleanEditorModel extends AEditorModel<Boolean> {

	public final boolean isTrue() {
		Boolean value = getValue();
		return value != null && value;
	}

}
