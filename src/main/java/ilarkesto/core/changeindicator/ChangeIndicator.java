package ilarkesto.core.changeindicator;

import java.util.ArrayList;
import java.util.List;

public class ChangeIndicator {

	private long changeTime = System.currentTimeMillis();
	private List<ChangeListener> changeListeners;

	public void markChanged() {
		changeTime = System.currentTimeMillis();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				listener.onChange();
			}
		}
	}

	public long getChangeTime() {
		return changeTime;
	}

	public boolean hasChangedSince(long time) {
		return changeTime > time;
	}

	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null) changeListeners = new ArrayList<ChangeListener>(1);
		changeListeners.add(listener);
	}

}
