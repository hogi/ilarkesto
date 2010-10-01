package ilarkesto.gwt.client;

public interface Predicate {

	boolean test();

	public static final Predicate FALSE = new Predicate() {

		@Override
		public boolean test() {
			return false;
		}
	};

	public static final Predicate TRUE = new Predicate() {

		@Override
		public boolean test() {
			return true;
		}
	};

}
