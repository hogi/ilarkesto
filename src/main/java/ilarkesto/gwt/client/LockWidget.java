package ilarkesto.gwt.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LockWidget extends SwitcherWidget {

	private Widget content;
	private Widget locker;

	public LockWidget(Widget content) {
		super(true);
		this.content = content;
		show(content);
	}

	public final void lock(Widget locker) {
		this.locker = locker;
		show(locker);
	}

	public final void lock(String message) {
		lock(createMessageLocker(message));
	}

	public final void unlock() {
		if (!isLocked()) return;
		show(content);
	}

	public final boolean isLocked() {
		return locker != null;
	}

	protected Widget createMessageLocker(String message) {
		return new Label(message);
	}

}
