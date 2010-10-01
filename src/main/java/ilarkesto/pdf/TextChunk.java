package ilarkesto.pdf;

public class TextChunk extends AParagraphElement {

	private StringBuilder text;

	public TextChunk(APdfElement parent) {
		super(parent);
	}

	public TextChunk text(Object s) {
		if (s == null) return this;
		if (text == null) text = new StringBuilder();
		text.append(s);
		return this;
	}

	public String getText() {
		if (text == null) return null;
		return text.toString();
	}

	private FontStyle fontStyle = DEFAULT_FONT_STYLE;

	public TextChunk setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle == null ? DEFAULT_FONT_STYLE : fontStyle;
		return this;
	}

	public FontStyle getFontStyle() {
		return fontStyle;
	}

	// --- dependencies ---

	public static final FontStyle DEFAULT_FONT_STYLE = new FontStyle();

}
