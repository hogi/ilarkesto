package ilarkesto.core.diff;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordDiff {

	private List<String> left;
	private List<String> right;
	private DiffMarker marker;

	private List<String> lcs;
	private int lcsLen;
	private int leftLen;
	private int rightLen;

	private StringBuilder out = new StringBuilder();
	private String chLcs;
	private String chLeft;
	private String chRight;

	private boolean skipBurn;

	public WordDiff(String left, String right, DiffMarker marker) {
		this(tokenize(left), tokenize(right), marker);
	}

	public WordDiff(List<String> left, List<String> right, DiffMarker marker) {
		super();
		this.left = left == null ? new ArrayList<String>(0) : left;
		this.right = right == null ? new ArrayList<String>(0) : right;
		this.marker = marker;
	}

	public WordDiff diff() {
		lcs = LongestCommonSubsequenceList.execute(left, right);
		updateLengths();

		while (skipBurn || (lcsLen > 0 && leftLen > 0 && rightLen > 0)) {
			burnNext();
		}
		if (leftLen == 0 && rightLen == 0) return this;
		if (leftLen == 0) {
			out.append(marker.added(concat(right)));
			return this;
		}
		if (rightLen == 0) {
			out.append(marker.removed(concat(left)));
			return this;
		}
		if (lcsLen == 0) {
			out.append(marker.removed(concat(left)));
			out.append(marker.added(concat(right)));
			return this;
		}

		return this;
	}

	private void burnNext() {
		if (!nextChar()) return;
		if (chLcs.equals(chLeft) && chLcs.equals(chRight)) {
			burnSame();
			return;
		}
		if (chLcs.equals(chLeft)) {
			burnAdded();
			return;
		}
		burnRemoved();
	}

	private void burnRemoved() {
		StringBuilder sb = new StringBuilder();
		while (!chLcs.equals(chLeft)) {
			sb.append(chLeft);
			if (!nextCharLeft()) break;
		}
		out.append(marker.removed(sb.toString()));
		skipBurn = true;
	}

	private void burnAdded() {
		StringBuilder sb = new StringBuilder();
		while (chLcs.equals(chLeft) && !chLcs.equals(chRight)) {
			sb.append(chRight);
			if (!nextCharRight()) break;
		}
		out.append(marker.added(sb.toString()));
		skipBurn = true;
	}

	private void burnSame() {
		StringBuilder sb = new StringBuilder();
		boolean nextCharAvailable = false;
		while (chLcs.equals(chLeft) && chLcs.equals(chRight)) {
			sb.append(chLcs);
			nextCharAvailable = nextChar();
			if (!nextCharAvailable) break;
		}
		out.append(marker.same(sb.toString()));
		if (nextCharAvailable) skipBurn = true;
	}

	private boolean nextChar() {
		if (skipBurn) {
			skipBurn = false;
			return true;
		}

		if (lcsLen == 0) return false;
		if (leftLen == 0) return false;
		if (rightLen == 0) return false;

		chLcs = lcs.get(0);
		chLeft = left.get(0);
		chRight = right.get(0);

		lcs.remove(0);
		left.remove(0);
		right.remove(0);

		lcsLen--;
		leftLen--;
		rightLen--;

		return true;
	}

	private boolean nextCharRight() {
		if (rightLen == 0) return false;

		chRight = right.get(0);

		right.remove(0);

		rightLen--;

		return true;
	}

	private boolean nextCharLeft() {
		if (leftLen == 0) return false;

		chLeft = left.get(0);

		left.remove(0);

		leftLen--;

		return true;
	}

	private void updateLengths() {
		leftLen = left.size();
		rightLen = right.size();
		lcsLen = lcs.size();
	}

	static List<String> tokenize(String s) {
		List<String> ret = new LinkedList<String>();
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

	static String concat(List<String> tokens) {
		StringBuilder sb = new StringBuilder();
		for (String token : tokens) {
			sb.append(token);
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return out.toString();
	}

}
