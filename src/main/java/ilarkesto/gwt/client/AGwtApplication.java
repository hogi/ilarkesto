package ilarkesto.gwt.client;

import ilarkesto.core.logging.Log;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public abstract class AGwtApplication implements EntryPoint {

	private static final Log log = Log.get(AGwtApplication.class);

	private static AGwtApplication singleton;

	public abstract void handleCommunicationError(Throwable ex);

	protected abstract void handleUnexpectedError(Throwable ex);

	protected abstract AGwtDao getDao();

	public AGwtApplication() {
		if (singleton != null) throw new RuntimeException("GWT application already instantiated: " + singleton);
		singleton = this;
		Log.setLogRecordHandler(new GwtLogRecordHandler());
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

			public void onUncaughtException(Throwable ex) {
				handleUnexpectedError(ex);
			}
		});
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

	public static AGwtApplication get() {
		return singleton;
	}

}
