package ilarkesto.gwt.client.undo;

import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.Gwt;

public class UndoAction extends AAction {

	private UndoManager undoManager;
	private AUndoOperation operation;

	public UndoAction(UndoManager undoManager, AUndoOperation operation) {
		this.undoManager = undoManager;
		this.operation = operation;
	}

	@Override
	public String getLabel() {
		return operation.getLongLabel();
	}

	@Override
	protected void onExecute() {
		undoManager.undo(operation);
		Gwt.update(Gwt.getRootWidget());
	}

}
