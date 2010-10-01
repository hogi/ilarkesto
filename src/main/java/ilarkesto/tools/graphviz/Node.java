package ilarkesto.tools.graphviz;

import java.util.HashMap;
import java.util.Map;

public class Node {

	private Map<String, String> properties = new HashMap<String, String>();
	private String name;

	Node(String name) {
		this.name = name;
		fontsize(10);
	}

	public Node label(String label) {
		return property("label", "\"" + label + "\"");
	}

	public Node color(String color) {
		return property("color", color);
	}

	public Node margin(float leftRight, float topBottom) {
		return property("margin", leftRight + "," + topBottom);
	}

	public Node shape(String shape) {
		return property("shape", shape);
	}

	public Node fontsize(int pts) {
		return property("fontsize", String.valueOf(pts));
	}

	public Node shapeBox() {
		return shape("box");
	}

	private Node property(String name, String value) {
		if (value != null) properties.put(name, value);
		return this;
	}

	String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
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
