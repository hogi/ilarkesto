package ilarkesto.form;

import java.util.Collection;

public interface MultiItem<E> {

	Collection<E> getItems();

	String getTooltip();

}
