package ilarkesto.gwt.client.editor;

public abstract class ATextEditorModel extends AEditorModel<String> {

	public boolean isRichtext() {
		return false;
	}

	public int getMaxLenght() {
		return isRichtext() ? Integer.MAX_VALUE : 110;
	}

	public boolean isMandatory() {
		return false;
	}

	public String getTemplate() {
		return null;
	}

}
