package ilarkesto.concurrent;

import java.util.Collection;
import java.util.Collections;

public abstract class ACollectionTask<E> extends ATask {

	private int count;
	private int index;
	private E element;

	protected abstract Collection<E> prepare() throws InterruptedException;

	protected abstract void perform(E element) throws InterruptedException;

	@Override
	protected final void perform() throws InterruptedException {
		Collection<E> elements = prepare();
		if (elements == null) elements = Collections.emptyList();
		count = elements.size();
		index = 0;
		for (E element : elements) {
			this.element = element;
			perform(element);
			if (isAbortRequested()) break;
			index++;
		}
		cleanup();
	}

	public final int getIndex() {
		return index;
	}

	protected void cleanup() throws InterruptedException {}

	protected String getProgressMessage(E element) {
		return element.toString();
	}

	@Override
	public final String getProgressMessage() {
		return getProgressMessage(element);
	}

	@Override
	public final float getProgress() {
		if (count == 0) return 1;
		if (index == 0) return 0;
		return (float) index / (float) count;
	}

}
