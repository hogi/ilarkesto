package ilarkesto.base;

public abstract class Assert {

	public static void greater(int a, int b) {
		if (a <= b) throw new AssertionException(a + " <= " + b);
	}

	public static void greaterOrEqual(int a, int b) {
		if (a < b) throw new AssertionException(a + " < " + b);
	}

	public static void equal(int a, int b) {
		if (a != b) throw new AssertionException(a + " != " + b);
	}

	public static void tru(boolean expression) {
		if (!expression) throw new AssertionException("expression is not true");
	}

	public static class AssertionException extends RuntimeException {

		AssertionException(String message) {
			super(message);
		}

	}

}
