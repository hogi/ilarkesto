package ilarkesto.core.menu;

import ilarkesto.core.changeindicator.ChangeListener;

public class StaticSubmenu extends StaticMenuItem implements Submenu<StaticMenuItem> {

	private StaticMenu submenu = new StaticMenu();

	public StaticSubmenu(String label) {
		super(label);
		setOnSelect(new OnSelect());
		setOnDeselect(new OnDeselect());
		submenu.getChangeIndicator().addChangeListener(new SubmenuChangeListener());
	}

	@Override
	public StaticMenu getMenu() {
		return submenu;
	}

	class OnSelect implements Runnable {

		@Override
		public void run() {
			if (submenu.getSelectedItem() == null) submenu.selectFirstItem();
		}
	}

	class OnDeselect implements Runnable {

		@Override
		public void run() {
			submenu.deselectAll();
		}
	}

	class SubmenuChangeListener implements ChangeListener {

		@Override
		public void onChange() {
			menu.getChangeIndicator().markChanged();
		}
	}

}
