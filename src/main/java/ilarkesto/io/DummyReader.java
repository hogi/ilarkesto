package ilarkesto.io;

import java.io.Reader;

public class DummyReader extends Reader {

	@Override
	public int read(char[] cbuf, int off, int len) {
		return -1;
	}

	@Override
	public void close() {}

}
