package ilarkesto.console;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ParameterMatcher {

	public static Object[] match(String input, Class<?>[] parameterTypes) {
		input = input.trim();
		int n = (input.isEmpty()) ? 0 : input.split(" ").length;
		if (n != parameterTypes.length)
			throw new IllegalArgumentException("Number of parameters differs in input and type list.");

		if (n == 0) { return new Object[] {}; }

		// prepare result
		Object[] parameters = new Object[n];

		Scanner tokens = new Scanner(input);
		for (int i = 0; i < n; i++) {
			Class<?> type = parameterTypes[i];
			try {
				if (type == byte.class || type == Byte.class) {
					parameters[i] = tokens.nextByte();
				} else if (type == short.class || type == Short.class) {
					parameters[i] = tokens.nextShort();
				} else if (type == int.class || type == Integer.class) {
					parameters[i] = tokens.nextInt();
				} else if (type == long.class || type == Long.class) {
					parameters[i] = tokens.nextLong();
				} else if (type == BigInteger.class) {
					parameters[i] = tokens.nextBigInteger();
				} else if (type == float.class || type == Float.class) {
					parameters[i] = tokens.nextFloat();
				} else if (type == double.class || type == Double.class) {
					parameters[i] = tokens.nextDouble();
				} else if (type == BigDecimal.class) {
					parameters[i] = tokens.nextBigDecimal();
				} else if (type == boolean.class || type == Boolean.class) {
					parameters[i] = tokens.nextBoolean();
				} else if (type == String.class) {
					parameters[i] = tokens.next();
				}
			} catch (InputMismatchException e) {
				// input does not match parameter list
				return null;
			}
		}
		return parameters;
	}
}
