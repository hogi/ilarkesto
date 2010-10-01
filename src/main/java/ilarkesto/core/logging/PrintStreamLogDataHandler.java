package ilarkesto.core.logging;

import java.io.PrintStream;

public class PrintStreamLogDataHandler implements LogRecordHandler {

	private PrintStream out;

	public PrintStreamLogDataHandler(PrintStream out) {
		super();
		this.out = out;
	}

	@Override
	public void log(LogRecord record) {
		out.println(record.toString());
	}

	@Override
	public void flush() {
	// out.flush();
	}
}
