package ilarkesto.core.menu;

import ilarkesto.base.Utl;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StaticMenuTest extends Assert {

	private StaticMenu menu;
	private StaticMenuItem item1;
	private StaticMenuItem item2;
	private StaticMenuItem item3;
	private StaticSubmenu item4;
	private StaticMenuItem item4_1;
	private StaticMenuItem item4_2;

	@BeforeMethod
	public void init() {
		menu = new StaticMenu();
		item1 = menu.addItem(new StaticMenuItem("Item 1"));
		item2 = menu.addItem(new StaticMenuItem("Item 2"));
		item3 = menu.addItem(new StaticMenuItem("Item 3"));
		item4 = menu.addItem(new StaticSubmenu("Item 4"));
		item4_1 = item4.getMenu().addItem(new StaticMenuItem("Item 4_1"));
		item4_2 = item4.getMenu().addItem(new StaticMenuItem("Item 4_2"));
	}

	@Test
	public void selection() {
		assertNull(menu.getSelectedItem());

		item2.select();

		assertSame(menu.getSelectedItem(), item2);
		assertFalse(item1.isSelected());
		assertTrue(item2.isSelected());
		assertFalse(item3.isSelected());
	}

	@Test
	public void events() {
		EventMock item1onSelect = new EventMock();
		EventMock item1onDeselect = new EventMock();
		item1.setOnSelect(item1onSelect);
		item1.setOnDeselect(item1onDeselect);

		EventMock item2onSelect = new EventMock();
		EventMock item2onDeselect = new EventMock();
		item2.setOnSelect(item2onSelect);
		item2.setOnDeselect(item2onDeselect);

		item1.select();

		assertTrue(item1onSelect.executed);
		assertFalse(item1onDeselect.executed);
		assertFalse(item2onSelect.executed);
		assertFalse(item2onDeselect.executed);

		item1onSelect = new EventMock();
		item1.setOnSelect(item1onSelect);

		item2.select();
		assertFalse(item1onSelect.executed);
		assertTrue(item1onDeselect.executed);
		assertTrue(item2onSelect.executed);
		assertFalse(item2onDeselect.executed);
	}

	@Test
	public void submenu() {
		Object payload = UUID.randomUUID();
		item4_2.setPayload(payload);

		assertSame(menu.getItemByPayload(payload), item4_2);
	}

	@Test
	public void submenuChangeIndicator() {
		long initTime = menu.getChangeIndicator().getChangeTime();
		assertFalse(menu.getChangeIndicator().hasChangedSince(initTime));
		item4_2.select();
		Utl.sleep(1);
		assertTrue(menu.getChangeIndicator().hasChangedSince(initTime));
	}

	private class EventMock implements Runnable {

		boolean executed;

		@Override
		public void run() {
			if (executed) throw new IllegalStateException("EventMock already executed");
			executed = true;
		}
	}
}
