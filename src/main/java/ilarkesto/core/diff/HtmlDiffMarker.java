package ilarkesto.core.diff;

import ilarkesto.core.base.Str;

public class HtmlDiffMarker implements DiffMarker {

	@Override
	public String same(String s) {
		return toHtml(s);
	}

	@Override
	public String added(String s) {
		return "<span class=\"added\">" + toHtml(s) + "</span>";
	}

	@Override
	public String removed(String s) {
		return "<span class=\"removed\">" + toHtml(s) + "</span>";
	}

	private String toHtml(String s) {
		return Str.toHtml(s);
	}

}
