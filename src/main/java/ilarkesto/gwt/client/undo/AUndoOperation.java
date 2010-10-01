package ilarkesto.gwt.client.undo;

import ilarkesto.gwt.client.DateAndTime;

public abstract class AUndoOperation {

	private DateAndTime dateAndTime;

	protected abstract void onUndo();

	public abstract String getLabel();

	public AUndoOperation() {
		dateAndTime = DateAndTime.now();
	}

	public final void undo() {
		onUndo();
	}

	public String getLongLabel() {
		return getLabel() + " (" + dateAndTime.getPeriodToNow().toShortestString() + " ago)";
	}

	@Override
	public String toString() {
		return "AUndoOperation(" + getLabel() + ")";
	}

}
