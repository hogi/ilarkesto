package ilarkesto.tools.graphviz;

import java.util.HashMap;
import java.util.Map;

public class Edge {

	private Map<String, String> properties = new HashMap<String, String>();
	private Node from;
	private Node to;

	Edge(Node from, Node to) {
		this.from = from;
		this.to = to;
	}

	public Edge label(String label) {
		properties.put("label", "\"" + label + "\"");
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(from.getName()).append(" -> ").append(to.getName());
		sb.append(" [ ");
		boolean first = true;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(entry.getKey()).append("=").append(entry.getValue());
		}
		sb.append(" ];");
		return sb.toString();
	}
}
