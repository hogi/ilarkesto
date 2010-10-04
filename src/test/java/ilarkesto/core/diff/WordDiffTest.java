package ilarkesto.core.diff;

import ilarkesto.testng.ATest;

import org.testng.annotations.Test;

public class WordDiffTest extends ATest {

	@Test
	public void same() {
		assertDiff("a", "a", "a");
		assertDiff("hello world", "hello world", "hello world");
	}

	@Test
	public void addedAtEnd() {
		assertDiff("a", "a b", "a[+ b]");
		assertDiff("hello", "hello world", "hello[+ world]");
	}

	@Test
	public void removedAtEnd() {
		assertDiff("a b", "a", "a[- b]");
		assertDiff("hello world", "hello", "hello[- world]");
	}

	@Test
	public void removedAtBeginning() {
		assertDiff("a b", "b", "[-a ]b");
		assertDiff("hello world", "world", "[-hello ]world");
	}

	@Test
	public void addedAtMiddle() {
		assertDiff("a c", "a b c", "a [+b ]c");
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

	@Test
	public void wordChange() {
		assertDiff("hello", "bye", "[hello|bye]");
		assertDiff("hello world", "bye world", "[hello|bye] world");
	}

	private static void assertDiff(String left, String right, String expectedDiff) {
		long begin = System.currentTimeMillis();
		TokenDiff diff = new TokenDiff(left, right, new TxtDiffMarker(), new WordTokenizer());
		diff.diff();
		String computedDiff = diff.toString();
		long end = System.currentTimeMillis();
		long duration = end - begin;
		if (duration > 1000) fail("Computing diff took longer than a second: " + duration + "ms.");
		assertEquals(computedDiff, expectedDiff);
	}

}
