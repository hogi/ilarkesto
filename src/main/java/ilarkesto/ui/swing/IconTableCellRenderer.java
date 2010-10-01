package ilarkesto.ui.swing;

import ilarkesto.swing.Swing;

import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

public class IconTableCellRenderer extends DefaultTableCellRenderer {

	public static final IconTableCellRenderer INSTANCE = new IconTableCellRenderer();

	@Override
	protected void setValue(Object value) {
		if (value instanceof String) value = Swing.getIcon16((String) value);
		setIcon((Icon) value);
	}
}
