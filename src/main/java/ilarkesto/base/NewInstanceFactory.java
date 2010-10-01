package ilarkesto.base;

/**
 * A simple factory implementation, which instantiates always the same class.
 */
public class NewInstanceFactory<E> implements Factory<E> {

	private Class<? extends E> type;

	public NewInstanceFactory(Class<? extends E> type) {
		this.type = type;
	}

	public E getBean() {
		try {
			return type.newInstance();
		} catch (InstantiationException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}

}
