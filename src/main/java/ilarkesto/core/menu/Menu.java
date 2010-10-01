package ilarkesto.core.menu;

import java.util.List;

public interface Menu<I extends MenuItem> {

	List<I> getItems();

	I getSelectedItem();

}
