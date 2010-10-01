package ilarkesto.core.menu;

import ilarkesto.core.changeindicator.ChangeIndicator;
import ilarkesto.core.changeindicator.HasChangeIndicator;

import java.util.ArrayList;
import java.util.List;

public class StaticMenu implements Menu<StaticMenuItem>, HasChangeIndicator {

	List<StaticMenuItem> items = new ArrayList<StaticMenuItem>();
	ChangeIndicator changeIndicator = new ChangeIndicator();

	@Override
	public List<StaticMenuItem> getItems() {
		return items;
	}

	@Override
	public StaticMenuItem getSelectedItem() {
		for (StaticMenuItem item : items) {
			if (item.isSelected()) return item;
		}
		return null;
	}

	public StaticMenuItem getItemByPayload(Object payload) {
		for (StaticMenuItem item : items) {
			if (item.isPayload(payload)) return item;
			if (item instanceof StaticSubmenu) {
				StaticSubmenu subMenu = (StaticSubmenu) item;
				StaticMenuItem foundItem = subMenu.getMenu().getItemByPayload(payload);
				if (foundItem != null) return foundItem;
			}
		}
		return null;
	}

	public <I extends StaticMenuItem> I addItem(I item) {
		item.setMenu(this);
		items.add(item);
		changeIndicator.markChanged();
		return item;
	}

	public void deselectAll() {
		for (StaticMenuItem item : items)
			item.deselect();
	}

	public void selectFirstItem() {
		if (items.isEmpty()) return;
		items.get(0).select();
	}

	@Override
	public ChangeIndicator getChangeIndicator() {
		return changeIndicator;
	}

}
