package ilarkesto.core.diff;

import java.util.LinkedList;
import java.util.List;

public class WordTokenizer implements DiffTokenizer {

	@Override
	public List<String> tokenize(String s) {
		List<String> ret = new LinkedList<String>();
		if (s == null) return ret;
		boolean word = false;
		StringBuilder token = null;
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (isWordChar(ch)) {
				if (token == null) {
					token = new StringBuilder();
					token.append(ch);
				} else {
					if (!word) {
						ret.add(token.toString());
						token = new StringBuilder();
					}
					token.append(ch);
				}
				word = true;
			} else {
				if (token == null) {
					token = new StringBuilder();
					token.append(ch);
				} else {
					if (word) {
						ret.add(token.toString());
						token = new StringBuilder();
					}
					token.append(ch);
				}
				word = false;
			}
		}
		if (token != null) ret.add(token.toString());
		return ret;
	}

	static boolean isWordChar(char ch) {
		return Character.isLetterOrDigit(ch);
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
