package ilarkesto.console;

import ilarkesto.base.Colors;
import ilarkesto.console.ConsoleApp.CallDescription;
import ilarkesto.console.ConsoleApp.ExecutionMode;
import ilarkesto.console.ConsoleApp.ParameterDescription;

/**
 * This is a Facade class for {@link Colors} demonstrating the use of {@link ConsoleApp}.
 * 
 * Methods in such a Facade class can also be used to handle complex input that needs to be converted to
 * objects that cannot be automatically casted from Strings.
 * 
 * The ConsoleApp is automatically created from public static methods of this class, using given Annotations.
 * Additionally, in {@link Colors}, the clean checking of input parameters is helpful, since
 * {@link ConsoleApp} catches and handles {@link IllegalArgumentException}.
 * 
 * @author ako
 */
public class ColorsConsoleApp {

	public static void main(String[] args) {
		ConsoleApp app = ConsoleApp.fromClass(ColorsConsoleApp.class);
		app.setExecutionMode(ExecutionMode.RUN_UNTIL_EXIT).showParameterNames();
		app.printUsage();
		app.execute();
	}

	@CallDescription(text = "Blends two colors a and b with the given ratio 0.0 <= r <= 1.0.")
	public static String blend(@ParameterDescription(name = "a") String a, @ParameterDescription(name = "b") String b,
			@ParameterDescription(name = "r") float r) {
		return Colors.blend(a, b, r);
	}

	@CallDescription(text = "Blends two colors a and b (with the ratio 0.5).")
	public static String blend(@ParameterDescription(name = "a") String a, @ParameterDescription(name = "b") String b) {
		return Colors.blend(a, b);
	}

	@CallDescription(text = "Darkens a color (by 0.1 where 0.0 is blac and 1.0 is white).")
	public static String darken(@ParameterDescription(name = "a") String a) {
		return Colors.darken(a);
	}

	@CallDescription(text = "Lightens a color (by 0.1 where 0.0 is blac and 1.0 is white).")
	public static String lighten(@ParameterDescription(name = "a") String a) {
		return Colors.lighten(a);
	}
}
