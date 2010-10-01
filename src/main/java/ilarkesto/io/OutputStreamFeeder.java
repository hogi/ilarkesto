package ilarkesto.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implementing class is responsible for writing to (feeding) an OutputStream.
 */
public interface OutputStreamFeeder {

	void feed(OutputStream out) throws IOException;

}
