package ilarkesto.base;

import java.util.Comparator;

/**
 * A Comparator wrapper, which inverts the order of the inner Comparator.
 */
public class ReverseComparator implements Comparator {

	private Comparator comparator;

	public ReverseComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	public final int compare(Object o1, Object o2) {
		return -comparator.compare(o1, o2);
	}

}
