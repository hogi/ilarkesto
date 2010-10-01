package ilarkesto.gwt.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class MultiSelectionWidget<I extends Object> extends AWidget {

	private FlexTable table;
	private Map<I, CheckBox> items = new HashMap<I, CheckBox>();
	private FocusPanel panel;

	@Override
	protected Widget onInitialization() {
		table = new FlexTable();
		table.setCellPadding(0);
		table.setCellSpacing(0);

		panel = new FocusPanel();
		panel.setStyleName("MultiSelectionWidget");
		panel.setWidget(table);
		return panel;
	}

	public void add(I item) {
		initialize();

		CheckBox checkbox;
		if (item instanceof HtmlLabelSupport) {
			checkbox = new CheckBox(((HtmlLabelSupport) item).getHtmlLabel(), true);
		} else {
			checkbox = new CheckBox(item.toString());
		}
		items.put(item, checkbox);
		table.setWidget(table.getRowCount(), 0, checkbox);
	}

	public void setItems(Collection<I> items) {
		initialize();
		clear();
		for (I item : items) {
			add(item);
		}
	}

	public void clear() {
		items.clear();
		table.clear();
	}

	public void setSelected(Collection<I> selectedItems) {
		for (Map.Entry<I, CheckBox> entry : items.entrySet()) {
			entry.getValue().setValue((selectedItems.contains(entry.getKey())));
		}
	}

	public List<I> getSelected() {
		List<I> ret = new LinkedList<I>();
		for (Map.Entry<I, CheckBox> entry : items.entrySet()) {
			if (entry.getValue().getValue()) ret.add(entry.getKey());
		}
		return ret;
	}

	public void addFocusListener(FocusListener focusListener) {
		initialize();
		panel.addFocusListener(focusListener);
	}

}
