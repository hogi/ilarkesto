package ilarkesto.core.menu;

import ilarkesto.core.logging.Log;

public class StaticMenuItem implements MenuItem {

	private Log log = Log.get(StaticMenuItem.class);

	private String label;
	private boolean selected;
	protected StaticMenu menu;
	private Object payload;

	private Runnable onSelect;
	private Runnable onDeselect;

	public StaticMenuItem(String label) {
		super();
		this.label = label;
	}

	void setMenu(StaticMenu menu) {
		this.menu = menu;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void select() {
		if (selected) return;
		log.debug("select(", this, ")");
		menu.deselectAll();
		selected = true;
		if (onSelect != null) onSelect.run();
		menu.changeIndicator.markChanged();
	}

	@Override
	public void deselect() {
		if (!selected) return;
		selected = false;
		if (onDeselect != null) onDeselect.run();
		menu.changeIndicator.markChanged();
	}

	public Object getPayload() {
		return payload;
	}

	public boolean isPayload(Object test) {
		if (test == null && payload == null) return true;
		if (test == null || payload == null) return false;
		return payload.equals(test);
	}

	public StaticMenuItem setPayload(Object payload) {
		this.payload = payload;
		if (menu != null) menu.changeIndicator.markChanged();
		return this;
	}

	public StaticMenuItem setOnSelect(Runnable onSelect) {
		this.onSelect = onSelect;
		return this;
	}

	public StaticMenuItem setOnDeselect(Runnable onDeselect) {
		this.onDeselect = onDeselect;
		return this;
	}

	@Override
	public String toString() {
		return selected ? "*" + getLabel() : getLabel();
	}

}
