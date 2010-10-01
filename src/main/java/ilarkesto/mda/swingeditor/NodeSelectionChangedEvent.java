package ilarkesto.mda.swingeditor;

import ilarkesto.core.event.AEvent;
import ilarkesto.mda.model.Node;

public class NodeSelectionChangedEvent extends AEvent {

	private Node selectedNode;

	public NodeSelectionChangedEvent(Node selectedNode) {
		super();
		this.selectedNode = selectedNode;
	}

	@Override
	public void tryToGetHandled(Object handler) {
		if (handler instanceof NodeSelectionChangedHandler) {
			log.info("Calling event handler:", handler);
			((NodeSelectionChangedHandler) handler).onNodeSelectionChanged(this);
		}
	}

	public Node getSelectedNode() {
		return selectedNode;
	}

}
