package ilarkesto.base;

import ilarkesto.core.logging.Log;

/**
 * Simple tool for measuring time between calls.
 */
public class TimeMonitor {

	private long start = System.currentTimeMillis();

	public long getTime() {
		return System.currentTimeMillis() - start;
	}

	public void debugOut(String name) {
		if (name == null) name = "TimeMonitor";
		Log.DEBUG(name, "->", this);
	}

	@Override
	public String toString() {
		return String.valueOf(getTime()) + " ms.";
	}

}
