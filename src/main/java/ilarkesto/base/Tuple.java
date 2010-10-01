package ilarkesto.base;

/**
 * Tuple of two objects.
 */
public final class Tuple<A, B> {

	private A a;
	private B b;

	public Tuple(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}

	public void setA(A a) {
		this.a = a;
	}

	public void setB(B b) {
		this.b = b;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Tuple)) return false;
		Tuple<A, B> other = (Tuple<A, B>) obj;
		return Sys.equals(a, other.a) && Sys.equals(b, other.b);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + (a == null ? 0 : a.hashCode());
		hash = hash * 31 + (b == null ? 0 : b.hashCode());
		return hash;
	}

	@Override
	public String toString() {
		return new StringBuilder().append('<').append(a).append(',').append(b).append('>').toString();
	}

}
