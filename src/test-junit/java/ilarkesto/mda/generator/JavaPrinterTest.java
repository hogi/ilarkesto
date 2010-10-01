package ilarkesto.mda.generator;

import java.util.Arrays;

import org.testng.annotations.Test;

public class JavaPrinterTest {

	@Test
	public void test() {
		JavaPrinter out = new JavaPrinter();

		out.package_("my.test");
		out.imports(Arrays.asList("java.util.*", "java.net.*"));
		out.beginClass("TestClass", "Object", null);

		out.protectedField("String", "name");

		out.endClass();

		System.err.println(out);
	}

}
