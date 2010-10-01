package ilarkesto.gwt.client.undo;

import ilarkesto.gwt.client.DropdownMenuButtonWidget;
import ilarkesto.gwt.client.Gwt;

import com.google.gwt.user.client.ui.Widget;

public class UndoButtonWidget extends DropdownMenuButtonWidget {

	private UndoManager undoManager;

	public UndoButtonWidget() {
		setLabel("Undo");
	}

	@Override
	protected Widget onInitialization() {
		undoManager = Gwt.getUndoManager();
		Widget widget = super.onInitialization();
		return Gwt.createDiv("UndoButtonWidget", widget);
	}

	@Override
	protected void onUpdate() {
		clear();
		if (undoManager != null) {
			for (AUndoOperation operation : undoManager.getOperations()) {
				addAction(new UndoAction(undoManager, operation));
			}
		}
		super.onUpdate();
	}

	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}

}
