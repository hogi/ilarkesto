package ilarkesto.gwt.client;

import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;

public abstract class AAction implements Command, ClickHandler {

	protected final Log log = Log.get(getClass());

	private Updatable[] widgetsToUpdate;

	public abstract String getLabel();

	protected abstract void onExecute();

	@Override
	public final void execute() {
		Log.DEBUG("Executing action: " + this);
		if (!isExecutable()) throw new RuntimeException("Action not executable: " + this);
		if (!isPermitted()) throw new RuntimeException("Action not permitted: " + this);
		onExecute();
		Gwt.update(Gwt.getRootWidget());
	}

	@Override
	public void onClick(ClickEvent event) {
		event.stopPropagation();
		execute();
	}

	public Image getIcon() {
		return null;
	}

	public boolean isExecutable() {
		return true;
	}

	public boolean isPermitted() {
		return true;
	}

	public String getTooltip() {
		return null;
	}

	public String getId() {
		return Str.getSimpleName(getClass()).replace('$', '_');
	}

	@Override
	public String toString() {
		return Str.getSimpleName(getClass());
	}

}
