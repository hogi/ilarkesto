package ilarkesto.logging;

import ilarkesto.base.Sys;
import ilarkesto.core.logging.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public abstract class JavaLogging {

	private static final Log LOG = Log.get(JavaLogging.class);

	public static void redirectToLoggers() {
		java.util.logging.Logger rootLogger = java.util.logging.Logger.getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		for (Handler handler : handlers) {
			rootLogger.removeHandler(handler);
		}
		rootLogger.addHandler(new LoggerRedirectionHandler());
		LOG.debug("Redirecting Java Logging to ilarkesto loggers");
	}

	public static void redirectToHomeFile(String name) {
		redirectToFile(new File(Sys.getUsersHomePath() + "/" + name + ".java.log"));
	}

	public static void redirectToFile(File file) {
		java.util.logging.Logger rootLogger = java.util.logging.Logger.getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		for (Handler handler : handlers) {
			rootLogger.removeHandler(handler);
		}
		rootLogger.addHandler(new FileRedirectionHandler(file));
		LOG.info("Redirecting Java Logging to file: " + file.getAbsolutePath());
	}

	public static Log.Level toLevel(Level javaLevel) {
		if (javaLevel == Level.FINE || javaLevel == Level.FINER || javaLevel == Level.FINEST || javaLevel == Level.OFF
				|| javaLevel == Level.INFO) {
			return Log.Level.DEBUG;
		} else if (javaLevel == Level.WARNING) {
			return Log.Level.WARN;
		} else if (javaLevel == Level.SEVERE) {
			return Log.Level.FATAL;
		} else {
			return Log.Level.INFO;
		}
	}

	private static void redirectToLogger(LogRecord record) {
		String loggerName = record.getLoggerName();
		int idx = loggerName.lastIndexOf('.');
		if (idx > 0) {
			loggerName = loggerName.substring(idx + 1);
		}
		Log logger = Log.get(loggerName);
		logger.log(toLevel(record.getLevel()), getMessage(record));
	}

	public static String getMessage(LogRecord record) {
		String msg = record.getMessage();
		Object[] parameters = record.getParameters();
		if (parameters != null && parameters.length > 0) {
			msg = MessageFormat.format(msg, parameters);
		}
		return msg;
	}

	private static class LoggerRedirectionHandler extends Handler {

		@Override
		public void close() throws SecurityException {}

		@Override
		public void flush() {
			Log.flush();
		}

		@Override
		public void publish(LogRecord record) {
			redirectToLogger(record);
		}

	}

	private static class FileRedirectionHandler extends Handler {

		private File file;
		private PrintWriter out;

		public FileRedirectionHandler(File file) {
			this.file = file;
		}

		private void createOut() {
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		@Override
		public void close() throws SecurityException {}

		@Override
		public void flush() {
			if (out == null) return;
			out.flush();
			Log.flush();
		}

		@Override
		public void publish(LogRecord record) {
			if (out == null) createOut();
			String loggerName = record.getLoggerName();
			int idx = loggerName.lastIndexOf('.');
			if (idx > 0) {
				loggerName = loggerName.substring(idx + 1);
			}
			out.print(record.getLevel());
			out.print(" ");
			out.print(loggerName);
			out.print(" ");
			out.println(record.getMessage());
			out.flush();
			Level level = record.getLevel();
			if (level == Level.SEVERE || level == Level.WARNING) {
				redirectToLogger(record);
			}
		}
	}

}
