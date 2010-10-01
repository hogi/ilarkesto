package ilarkesto.logging;

import ilarkesto.core.logging.Log;

import java.lang.Thread.UncaughtExceptionHandler;

public class LoggingUncaughtExceptionHandler implements UncaughtExceptionHandler {

	public static final LoggingUncaughtExceptionHandler INSTANCE = new LoggingUncaughtExceptionHandler();

	private static final Log LOG = Log.get(LoggingUncaughtExceptionHandler.class);

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LOG.error("Exception in thread:", t, "->", e);
	}

}
