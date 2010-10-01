package ilarkesto.gwt.client;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class DropdownMenuButtonWidget extends AWidget {

	private LinkedHashMap<String, List<AAction>> actionGroups;
	// private List<AAction> actions;
	private MenuBar menu;
	private MenuItem popup;
	private String icon;
	private String label;

	@Override
	protected Widget onInitialization() {
		actionGroups = new LinkedHashMap<String, List<AAction>>();

		menu = new MenuBar(true);

		MenuBar menuBar = new MenuBar();
		String title = "<img src='dropdown.png' alt='Dropdown Menu' width='16' height='16'>";
		if (icon != null) title = "<img src='" + icon + "' width='16' height='16' alt='ico'>" + title;
		if (label != null) title = "<div style='float: left; margin-top: 4px;'>" + label + "</div> " + title;
		popup = menuBar.addItem(title, true, menu);
		menuBar.setPopupPosition(MenuBar.PopupPosition.LEFT);

		Widget wrapper = TableBuilder.row(false, 0, menuBar);
		wrapper.setStyleName("DropdownMenuButtonWidget");
		return wrapper;
	}

	@Override
	protected void onUpdate() {
		menu.clearItems();
		if (actionGroups.isEmpty()) {
			menu.setVisible(false);
		} else {
			menu.setVisible(true);
			int i = 0;
			for (List<AAction> actions : actionGroups.values()) {
				for (AAction action : actions) {
					if (!action.isExecutable()) continue;

					MenuItem menuItem;
					if (action.isPermitted()) {
						menuItem = new MenuItem(action.getLabel(), action);
					} else {
						menuItem = new MenuItem(action.getLabel(), (Command) null);
						menuItem.addStyleName("MenuItem-disabled");
					}
					menuItem.setTitle(action.getTooltip());
					// Gwt.addHtmlTooltip(menuItem, action.getTooltip());
					menu.addItem(menuItem);
				}
				i++;
				if (i < actionGroups.size()) menu.addSeparator();
			}
		}
	}

	public void addAction(AAction action) {
		addAction("default", action);
	}

	public void addAction(String groupName, AAction action) {
		initialize();
		if (actionGroups.get(groupName) == null) {
			actionGroups.put(groupName, new LinkedList<AAction>());
		}
		actionGroups.get(groupName).add(action);
	}

	public void addSeparator() {
		menu.addSeparator();
	}

	public void clear() {
		initialize();
		actionGroups.clear();
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
