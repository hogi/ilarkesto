package ilarkesto.form;

import ilarkesto.base.Str;
import ilarkesto.richtext.RichTextFormatter;

public class TextareaFormField extends TextFormField {

	public TextareaFormField(String name) {
		super(name);
	}

	@Override
	protected String preProcessValue(String s) {
		if (s == null) return null;
		if (!html) return super.preProcessValue(s);
		if (s.startsWith("<html")) {
			String plain = Str.removeHtmlTags(s).trim();
			if (plain.length() == 0 && !s.toLowerCase().contains("<img")) s = plain;
			if (s.length() > 0) s = "<html>" + Str.cutHtmlAndHeaderAndBody(s);
		}
		return super.preProcessValue(s);
	}

	@Override
	public TextFormField setValue(String value) {
		if (!html) return super.setValue(value);
		if (value == null || value.startsWith("<html")) return super.setValue(value);
		return super.setValue(RichTextFormatter.toHtml(value));
	}

	private int lines = 15;

	public TextareaFormField setLines(int value) {
		this.lines = value;
		return this;
	}

	public int getLines() {
		return lines;
	}

	private boolean html = true;

	public boolean isHtml() {
		return html;
	}

	public TextareaFormField setHtml(boolean html) {
		this.html = html;
		return this;
	}

	public TextareaFormField setForceHtml(boolean forceHtml) {
		if (forceHtml) setHtml(true);
		return this;
	}

	public boolean isForceHtml() {
		return true; // TODO forceHtml;
	}

}
