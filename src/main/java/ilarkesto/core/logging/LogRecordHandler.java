package ilarkesto.core.logging;

public interface LogRecordHandler {

	void log(LogRecord record);

	void flush();
}
