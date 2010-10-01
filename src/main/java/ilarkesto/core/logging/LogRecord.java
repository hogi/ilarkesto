package ilarkesto.core.logging;

import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log.Level;

public class LogRecord {

	public final String name;
	public final Level level;
	public final Object[] parameters;
	public String context;

	public LogRecord(String name, Level level, Object[] parameters) {
		super();
		this.name = name;
		this.level = level;
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		String nameFormated = Str.cutLeft(name, 20);
		nameFormated = Str.fillUpRight(nameFormated, " ", 20);

		StringBuilder sb = new StringBuilder();

		// level
		if ((level != Level.DEBUG) && (level != Level.INFO)) sb.append("\n    ");
		if (level != Level.DEBUG) sb.append(level);

		// logger
		sb.append(" ").append(nameFormated);

		// text
		StringBuilder text = new StringBuilder();
		if (parameters == null) {
			text.append(" <null>");
		} else {
			for (Object parameter : parameters) {
				text.append(' ');
				if (parameter instanceof Throwable) {
					text.append("\n").append(Str.getStackTrace((Throwable) parameter));
				} else {
					text.append(Str.format(parameter));
				}
			}
		}
		sb.append(Str.fillUpRight(text.toString(), " ", 100));

		// context
		if (context != null) sb.append(" | ").append(context);

		// extra line for high prio logs
		if ((level != Level.DEBUG) && (level != Level.INFO)) sb.append('\n');

		return sb.toString();
	}

}
