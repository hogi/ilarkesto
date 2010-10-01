package ilarkesto.richtext;

import ilarkesto.base.Str;

public class RichTextFormatter {

	public static String toHtml(String s) {
		if (s == null) return null;
		if (!s.startsWith("<html")) {
			s = Str.replaceForHtml(s);
		}
		String html = Str.cutHtmlAndHeaderAndBody(s);
		html = Str.activateLinksInHtml(html);
		return html;
	}

}
