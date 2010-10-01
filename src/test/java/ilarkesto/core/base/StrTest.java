package ilarkesto.core.base;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StrTest extends Assert {

	@Test
	public void getLeadingSpaces() {
		assertEquals(Str.getLeadingSpaces("   a"), "   ");
		assertEquals(Str.getLeadingSpaces("   "), "   ");
		assertEquals(Str.getLeadingSpaces("a"), "");
		assertEquals(Str.getLeadingSpaces(""), "");
	}

	@Test
	public void cutFromTo() {
		assertEquals(Str.cutFromTo("Hello <em>world</em>!", "<em>", "</em>"), "world");
	}

}
