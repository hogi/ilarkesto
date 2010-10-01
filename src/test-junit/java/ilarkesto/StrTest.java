package ilarkesto;

import ilarkesto.base.Str;
import junit.framework.TestCase;

public class StrTest extends TestCase {

	public void testRemovePostfix() {
		assertEquals("my", Str.removeSuffix("myTest", "Test"));
		assertEquals("myTest", Str.removeSuffix("myTest", "Tes"));
		assertEquals("myTest2", Str.removeSuffix("myTest2", "Test"));
	}

	public void testGetTokenAfter() {
		assertEquals("result", Str.getTokenAfter("ein test, um 'result' zu finden", " ,'", "test", 1));
	}

	// --- dependencies ---

}
