package ilarkesto.swing;

import ilarkesto.core.logging.Log;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HorizontalBarPanel extends JPanel {

	private static final Log LOG = Log.get(HorizontalBarPanel.class);

	public static void main(String[] args) {
		HorizontalBarPanel panel = new HorizontalBarPanel();
		panel.addColumn(new JLabel("Column 1"));
		panel.addColumn(new JLabel("Column 2"));
		panel.addColumn(new JLabel("Column 3"));
		panel.addColumn(new JLabel("Column 4"));
		Swing.showInJFrame(panel);
	}

	private JPanel grid;

	public HorizontalBarPanel() {
		setLayout(new BorderLayout());
		add(createGrid(), BorderLayout.CENTER);
	}

	public void addColumn(Component component) {
		LOG.debug("addColumn:", component);
		grid.add(component);
		grid.updateUI();
	}

	public void removeColumn(Component component) {
		LOG.debug("removeColumn:", component);
		grid.remove(component);
		grid.updateUI();
	}

	public void removeColumnsAfter(Component component) {
		LOG.debug("removeColumnsAfter:", component);
		int count = grid.getComponentCount();
		for (int i = count - 1; i >= 0; i--) {
			if (grid.getComponent(i) == component) break;
			grid.remove(i);
		}
		grid.updateUI();
	}

	public void removeAllColumns() {
		LOG.debug("removeAllColumns");
		grid.removeAll();
	}

	private Component createGrid() {
		grid = new JPanel(new GridLayout(1, 0, 10, 0));

		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.add(grid, BorderLayout.WEST);

		JScrollPane scroller = new JScrollPane(wrapper);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		return scroller;
	}

}
