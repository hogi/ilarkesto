package ilarkesto.mda.swingeditor;

import ilarkesto.core.event.EventBus;
import ilarkesto.core.logging.Log;
import ilarkesto.core.scope.In;
import ilarkesto.core.scope.Init;
import ilarkesto.mda.model.ModelChangedEvent;
import ilarkesto.mda.model.ModelChangedHandler;
import ilarkesto.mda.model.ModellingSession;
import ilarkesto.mda.model.Node;
import ilarkesto.mda.swingeditor.NodeListPanel.Observer;
import ilarkesto.swing.HorizontalBarPanel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NodeListBarPanel extends JPanel implements Observer, ModelChangedHandler {

	private static Log log = Log.get(NodeListBarPanel.class);

	@In
	ModellingSession modellingSession;

	@In
	SwingModelHelper swingModelHelper;

	@In
	EventBus eventBus;

	private HorizontalBarPanel bar;

	public NodeListBarPanel() {
		bar = new HorizontalBarPanel();

		setLayout(new BorderLayout());
		add(bar, BorderLayout.CENTER);
		setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	@Init
	public void rebuild() {
		log.debug("rebuild()");
		bar.removeAllColumns();
		bar.addColumn(createNodeList(modellingSession.getModel().getRoot()));
	}

	@Override
	public void onModelChanged(ModelChangedEvent event) {
		rebuild();
	}

	private NodeListPanel createNodeList(Node node) {
		NodeListPanel nodeList = new NodeListPanel(swingModelHelper);
		nodeList.setObserver(this);
		nodeList.setNode(node);
		return nodeList;
	}

	@Override
	public void onNodeSelectionChanged(NodeListPanel nodeListPanel, Node selectedNode) {
		eventBus.fireEvent(new NodeSelectionChangedEvent(selectedNode));
		bar.removeColumnsAfter(nodeListPanel);
		if (selectedNode != null) {
			if (!selectedNode.containsChildren()) {
				if (!modellingSession.getRuleSet().containsAllowedChildTypes(selectedNode)) return;
			}
			bar.addColumn(createNodeList(selectedNode));
		}
	}

}
