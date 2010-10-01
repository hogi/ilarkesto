package ilarkesto.fp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class FPTest extends TestCase {

	public void testGroup() {
		Collection<String> elements = new ArrayList<String>();
		elements.add("abc");
		elements.add("123");
		elements.add("456");

		Function<String, String> textOrNumGroupFunction = new Function<String, String>() {

			public String eval(String e) {
				try {
					Integer.parseInt(e);
					return "num";
				} catch (NumberFormatException ex) {
					return "text";
				}
			}

		};

		Map<String, List<String>> result = FP.group(elements, textOrNumGroupFunction);
		List<String> nums = result.get("num");
		List<String> texts = result.get("text");
		List<String> others = result.get("other");

		assertNotNull(texts);
		assertNotNull(nums);
		assertNull(others);

		assertEquals(1, texts.size());
		assertEquals(2, nums.size());

		assertTrue(texts.contains("abc"));
		assertTrue(nums.contains("123"));
		assertFalse(nums.contains("abc"));
	}

}
