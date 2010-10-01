package ilarkesto.io;

import ilarkesto.base.Str;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CsvWriter {

	private PrintWriter out;

	private List<String> headers;

	public CsvWriter(PrintWriter out) {
		this.out = out;
	}

	public CsvWriter(Writer out) {
		this(new PrintWriter(out));
	}

	public void writeRecord(Map<String, Object> fields) {
		if (headers == null)
			throw new IllegalStateException("headers property must be set when to write record values from a map");
		for (String header : headers)
			writeField(fields.get(header));
		closeRecord();
	}

	public void writeHeaders(List<String> headers) {
		setHeaders(headers);
		writeRecord(headers);
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public void writeRecord(Collection<String> values) {
		for (String value : values)
			writeField(value);
		closeRecord();
	}

	private boolean nl = true;

	public void writeField(Object value) {
		if (!nl) {
			out.print(separator);
		}
		nl = false;
		if (value == null) {
			// value = "";
			return;
		}
		out.print('"');
		out.print(escape(value.toString()));
		out.print('"');
	}

	public void closeRecord() {
		out.print("\r\n");
		out.flush();
		nl = true;
	}

	public static String escape(String value) {
		value = Str.escapeEscapeSequences(value);
		return value;
	}

	public void close() {
		out.close();
	}

	// --- dependencies ---

	private char separator = ',';

	public void setSeparator(char separator) {
		this.separator = separator;
	}

}
