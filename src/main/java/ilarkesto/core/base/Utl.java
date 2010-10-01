package ilarkesto.core.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public class Utl {

	public static <T> List<T> toList(T... elements) {
		if (elements == null) return null;
		List<T> ret = new ArrayList<T>(elements.length);
		for (T element : elements) {
			if (element == null) continue;
			ret.add(element);
		}
		return ret;
	}

	public static boolean equals(Object a, Object b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return a.equals(b);
	}

	public static int compare(int i1, int i2) {
		if (i1 > i2) return 1;
		if (i1 < i2) return -1;
		return 0;
	}

	public static int compare(Comparable a, Comparable b) {
		if (a == null && b == null) return 0;
		if (a == null && b != null) return -1;
		if (a != null && b == null) return 1;
		return a.compareTo(b);
	}

	public static int parseHex(String hex) {
		return Integer.parseInt(hex, 16);
	}

	public static String concatToHtml(Collection<? extends ToHtmlSupport> items, String separator) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (ToHtmlSupport entity : items) {
			if (first) {
				first = false;
			} else {
				sb.append(separator);
			}
			sb.append(entity.toHtml());
		}
		return sb.toString();
	}

	@Deprecated
	public static String getSimpleName(Class type) {
		return Str.getSimpleName(type);
	}

	public static <T> List<T> toList(Enumeration<T> e) {
		if (e == null) return null;
		List<T> ret = new ArrayList<T>();
		while (e.hasMoreElements()) {
			ret.add(e.nextElement());
		}
		return ret;
	}

}
