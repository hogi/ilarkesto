package ilarkesto.base;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author K. Grubalski
 */
public class EndlessList<T> implements Iterator<T>, Iterable<T> {

	private ArrayList<T> list;

	private int idx = -1;

	private boolean reset = false;

	public EndlessList() {
		list = new ArrayList<T>();
	}

	public boolean hasNext() {
		if (reset) {
			reset = false;
			idx = -1;
			return false;
		}
		if (list.size() < 1) return false;
		if (idx >= list.size()) {
			idx = -1;
			return false;
		}
		return true;
	}

	public T next() {
		reset = false;
		idx++;
		if (idx >= list.size()) {
			idx = 0;
		} else if (idx == list.size() - 1) {
			reset = true;
		}
		return list.get(idx);
	}

	public void add(T value) {
		list.add(value);
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Needed to work with for-each-loop.
	 */
	public Iterator iterator() {
		return this;
	}

	public static void main(String[] args) {
		EndlessList l = new EndlessList();
		l.add("erstes");
		l.add("zweites");
		l.add("drittes");
		l.add("viertes");

		// for (String string : l) {
		// System.out.println(string);
		// }

		System.out.println(l.next());
		System.out.println(l.next());
		System.out.println(l.next());
		System.out.println(l.next());
		System.out.println(l.next());
		System.out.println(l.next());
		System.out.println(l.next());

	}

}
