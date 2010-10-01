package ilarkesto.mda.model;

import ilarkesto.core.logging.Log;

import java.util.Comparator;

public class NodeByIndexComparator implements Comparator<Node>, NodeTypes {

	private static Log log = Log.get(NodeByIndexComparator.class);

	@Override
	public int compare(Node a, Node b) {
		int ai = parse(a.getChildValueByType(Index));
		int bi = parse(b.getChildValueByType(Index));
		if (ai == bi) return 0;
		return ai > bi ? 1 : -1;
	}

	private int parse(String value) {
		if (value == null) return Integer.MAX_VALUE;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			log.warn("Failed to parse Index:", value);
			return Integer.MAX_VALUE;
		}
	}
}
