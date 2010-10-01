package ilarkesto.base;

import java.util.Comparator;

public class ToStringComparator implements Comparator {

	public static final ToStringComparator INSTANCE = new ToStringComparator(false);
	public static final ToStringComparator INSTANCE_IGNORECASE = new ToStringComparator(true);

	private boolean ignoreCase;

	public ToStringComparator(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	@Override
	public int compare(Object a, Object b) {
		if (ignoreCase) {
			return a.toString().compareToIgnoreCase(b.toString());
		} else {
			return a.toString().compareTo(b.toString());
		}
	}

}
