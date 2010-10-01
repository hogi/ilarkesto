package ilarkesto.mda.swingeditor;

import ilarkesto.core.scope.In;
import ilarkesto.mda.model.Node;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class NodeValuePanel extends JPanel implements NodeSelectionChangedHandler {

	@In
	SwingModelHelper swingModelHelper;

	public NodeValuePanel() {
		super(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	@Override
	public void onNodeSelectionChanged(NodeSelectionChangedEvent event) {
		removeAll();
		Node node = event.getSelectedNode();
		if (node != null) {
			Component component = swingModelHelper.createValueComponent(node);
			add(createWrapper(component), BorderLayout.CENTER);
		}
		updateUI();
	}

	private Component createWrapper(Component component) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(component, BorderLayout.CENTER);
		panel.setBorder(new LineBorder(Color.DARK_GRAY));
		return panel;
	}

}
