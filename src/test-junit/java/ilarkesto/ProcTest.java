package ilarkesto;

import ilarkesto.base.Proc;
import junit.framework.TestCase;

public class ProcTest extends TestCase {

	public void test() {
		Proc proc = new Proc("java");
		proc.addParameter("-version");
		proc.start();
		proc.waitFor();
		System.out.println(proc.getReturnCode());
		System.out.println(proc.getOutput());
	}

}
