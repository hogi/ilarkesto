package ilarkesto.gwt.client.editor;

public abstract class AIntegerEditorModel extends AEditorModel<Integer> {

	public abstract void increment();

	public abstract void decrement();

	public boolean isMandatory() {
		return false;
	}

	public int getMin() {
		return 0;
	}

	public int getMax() {
		return Integer.MAX_VALUE;
	}

}
