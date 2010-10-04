package ilarkesto.core.diff;

import java.util.LinkedList;
import java.util.List;

public class LineTokenizer implements DiffTokenizer {

	@Override
	public List<String> tokenize(String s) {
		List<String> ret = new LinkedList<String>();
		if (s == null) return ret;
		int len = s.length();
		int from = 0;
		int to = s.indexOf('\n');
		while (to >= 0) {
			ret.add(s.substring(from, to));
			ret.add("\n");
			from = to + 1;
			if (from >= len) return ret;
			to = s.indexOf('\n', from);
		}
		ret.add(s.substring(from));
		return ret;
	}

	@Override
	public String concat(List<String> tokens) {
		StringBuilder sb = new StringBuilder();
		for (String token : tokens) {
			sb.append(token);
		}
		return sb.toString();
	}

}
