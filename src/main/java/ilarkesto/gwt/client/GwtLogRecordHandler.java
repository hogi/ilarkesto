package ilarkesto.gwt.client;

import ilarkesto.core.logging.LogRecord;
import ilarkesto.core.logging.LogRecordHandler;

import com.google.gwt.core.client.GWT;

public class GwtLogRecordHandler implements LogRecordHandler {

	@Override
	public void log(LogRecord record) {
		if (GWT.isScript()) return;
		System.out.println(record.toString());
	}

	@Override
	public void flush() {}

}
