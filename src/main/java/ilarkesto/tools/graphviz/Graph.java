package ilarkesto.tools.graphviz;

import java.util.ArrayList;
import java.util.List;

public class Graph {

	private List<Node> nodes = new ArrayList<Node>();
	private List<Edge> edges = new ArrayList<Edge>();
	private int nodeCount;

	public Node node(String label) {
		Node node = new Node("n" + ++nodeCount);
		node.label(label);
		nodes.add(node);
		return node;
	}

	public Edge edge(Node from, Node to) {
		Edge edge = new Edge(from, to);
		edges.add(edge);
		return edge;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		for (Node node : nodes)
			sb.append("  ").append(node).append("\n");
		for (Edge edge : edges) {
			sb.append("  ").append(edge).append("\n");
		}
		sb.append("}\n");
		return sb.toString();
	}
}
