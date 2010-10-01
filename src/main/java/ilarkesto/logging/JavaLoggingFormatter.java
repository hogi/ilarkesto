package ilarkesto.logging;

import ilarkesto.base.Str;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JavaLoggingFormatter extends Formatter {

	private static final int LOGGER_WIDTH = 30;

	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		sb.append(Str.fillUpRight(Str.cutRight(record.getLevel().getName(), 4), " ", 5));
		sb.append(Str.fillUpRight(Str.cutLeft(record.getLoggerName(), LOGGER_WIDTH - 1, ".."), " ", LOGGER_WIDTH));
		sb.append("-> ");
		sb.append(record.getMessage());
		sb.append("\n");
		if (record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter out = new PrintWriter(sw);
			record.getThrown().printStackTrace(out);
			out.flush();
			sb.append(sw.toString());
		}
		return sb.toString();
	}

	public static final JavaLoggingFormatter INSTANCE = new JavaLoggingFormatter();

	public static void install() {
		Handler[] handler = Logger.getLogger("").getHandlers();
		for (int i = 0; i < handler.length; i++) {
			handler[i].setFormatter(INSTANCE);
		}
	}

}
