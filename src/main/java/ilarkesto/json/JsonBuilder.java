package ilarkesto.json;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class JsonBuilder {

	private SortedMap<String, Object> elements = new TreeMap<String, Object>();

	public void string(String name, String string) {
		elements.put(name, '"' + string + '"');
	}

	public JsonBuilder object(String name) {
		JsonBuilder object = new JsonBuilder();
		elements.put(name, object);
		return object;
	}

	@Override
	public String toString() {
		return toString(0);
	}

	private String toString(int indent) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		indent++;
		for (Map.Entry<String, Object> entry : elements.entrySet()) {
			indent(sb, indent);
			sb.append("\"").append(entry.getKey()).append("\" : ");
			Object o = entry.getValue();
			if (o instanceof JsonBuilder) {
				sb.append(((JsonBuilder) o).toString(indent));
			} else {
				sb.append(o);
			}
			sb.append(",\n");
		}
		indent--;
		indent(sb, indent);
		sb.append("}");
		return sb.toString();
	}

	private void indent(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++)
			sb.append("  ");
	}

}
