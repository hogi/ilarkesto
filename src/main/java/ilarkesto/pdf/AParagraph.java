package ilarkesto.pdf;

import ilarkesto.base.Str;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AParagraph extends APdfElement {

	public enum Align {
		RIGHT
	}

	private FontStyle defaultFontStyle;
	protected float height;
	protected Align align;
	protected List<AParagraphElement> elements = new ArrayList<AParagraphElement>(1);

	public abstract AImage image(File file);

	public abstract AImage image(byte[] data);

	public AParagraph(APdfElement parent) {
		super(parent);
	}

	protected List<AParagraphElement> getElements() {
		return elements;
	}

	public AParagraph html(String html, FontStyle fontStyle) {
		return text(Str.html2text(html), fontStyle);
	}

	public AParagraph html(String html) {
		return text(Str.html2text(html));
	}

	public AParagraph text(Object text, FontStyle fontStyle) {
		if (text == null) return this;
		elements.add(new TextChunk(this).text(text).setFontStyle(fontStyle));
		return this;
	}

	public AParagraph text(Object text) {
		return text(text, defaultFontStyle);
	}

	public AParagraph nl() {
		text("\n");
		return this;
	}

	public AParagraph nl(FontStyle fontStyle) {
		text("\n", fontStyle);
		return this;
	}

	public AParagraph setHeight(float height) {
		this.height = height;
		return this;
	}

	public AParagraph setAlign(Align align) {
		this.align = align;
		return this;
	}

	public AParagraph setDefaultFontStyle(FontStyle defaultFontStyle) {
		this.defaultFontStyle = defaultFontStyle;
		return this;
	}

	// --- dependencies ---

}
