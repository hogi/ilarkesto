package ilarkesto.fp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class And<E> implements Predicate<E> {

	private List<Predicate<E>>	predicates	= new ArrayList<Predicate<E>>();

	public And() {}

	public And(Predicate<E>... predicates) {
		this.predicates.addAll(Arrays.asList(predicates));
	}

	public void add(Predicate<E> predicate) {
		predicates.add(predicate);
	}

	public boolean test(E parameter) {
		for (Predicate<E> predicate : predicates) {
			if (!predicate.test(parameter)) return false;
		}
		return true;
	}

}
