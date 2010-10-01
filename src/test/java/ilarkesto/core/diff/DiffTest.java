package ilarkesto.core.diff;

import ilarkesto.core.diff.Diff;
import ilarkesto.core.diff.TxtDiffMarker;
import ilarkesto.testng.ATest;

import org.testng.annotations.Test;

public class DiffTest extends ATest {

	@Test
	public void same() {
		assertDiff("a", "a", "a");
		assertDiff("hello world", "hello world", "hello world");
	}

	@Test
	public void addedAtEnd() {
		assertDiff("a", "ab", "a[+b]");
		assertDiff("hello", "hello world", "hello[+ world]");
	}

	@Test
	public void removedAtEnd() {
		assertDiff("ab", "a", "a[-b]");
		assertDiff("hello world", "hello", "hello[- world]");
	}

	@Test
	public void removedAtBeginning() {
		assertDiff("ab", "b", "[-a]b");
		assertDiff("hello world", "world", "[-hello ]world");
	}

	@Test
	public void addedAtMiddle() {
		assertDiff("ac", "abc", "a[+b]c");
		assertDiff("hello world", "hello happy world", "hello [+happy ]world");
	}

	@Test
	public void addedFromNothing() {
		assertDiff(null, "a", "[+a]");
		assertDiff(null, "hello world", "[+hello world]");
	}

	@Test
	public void removedToNothing() {
		assertDiff("a", null, "[-a]");
		assertDiff("hello world", null, "[-hello world]");
	}

	private static void assertDiff(String left, String right, String expectedDiff) {
		Diff diff = new Diff(left, right, new TxtDiffMarker());
		diff.diff();
		assertEquals(diff.toString(), expectedDiff);
	}

}
