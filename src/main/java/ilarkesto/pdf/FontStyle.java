package ilarkesto.pdf;

import java.awt.Color;

public final class FontStyle {

	private String font = "Helvetica";
	private float size = 4f;
	private boolean italic;
	private boolean bold;
	private Color color = Color.BLACK;

	public boolean isItalic() {
		return italic;
	}

	public FontStyle setItalic(boolean italic) {
		this.italic = italic;
		return this;
	}

	public boolean isBold() {
		return bold;
	}

	public FontStyle setBold(boolean bold) {
		this.bold = bold;
		return this;
	}

	public float getSize() {
		return size;
	}

	public FontStyle setSize(float size) {
		this.size = size;
		return this;
	}

	public Color getColor() {
		return color;
	}

	public FontStyle setColor(Color color) {
		this.color = color;
		return this;
	}

	public String getFont() {
		return font;
	}

	public FontStyle setFont(String font) {
		this.font = font;
		return this;
	}

	// --- ---

	public FontStyle(FontStyle style) {
		this.font = style.font;
		this.size = style.size;
		this.bold = style.bold;
		this.italic = style.italic;
		this.color = style.color;
	}

	public FontStyle() {}

}
