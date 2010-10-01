package ilarkesto.io;

import java.io.OutputStream;

public class StringOutputStream extends OutputStream {

	private StringBuilder sb = new StringBuilder();

	@Override
	public void write(int b) {
		sb.append((char) b);
	}

	@Override
	public String toString() {
		return sb.toString();
	}

}
