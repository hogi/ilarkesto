package ilarkesto.pdf;

import java.awt.Color;

public abstract class ACell extends APdfContainerElement {

	private Color backgroundColor;

	private Color borderTopColor;
	private Color borderBottomColor;
	private Color borderLeftColor;
	private Color borderRightColor;

	private float borderTopWidth;
	private float borderBottomWidth;
	private float borderLeftWidth;
	private float borderRightWidth;

	private float paddingTop = 0;
	private float paddingBottom = 1;
	private float paddingLeft = 1;
	private float paddingRight = 1;

	private FontStyle fontStyle;

	public ACell(APdfElement parent) {
		super(parent);
	}

	public ACell setBorderTop(Color color, float width) {
		borderTopColor = color;
		borderTopWidth = width;
		return this;
	}

	public ACell setBorderBottom(Color color, float width) {
		borderBottomColor = color;
		borderBottomWidth = width;
		return this;
	}

	public ACell setBorderLeft(Color color, float width) {
		borderLeftColor = color;
		borderLeftWidth = width;
		return this;
	}

	public ACell setBorderRight(Color color, float width) {
		borderRightColor = color;
		borderRightWidth = width;
		return this;
	}

	public ACell setBorder(Color color, float width) {
		setBorderTop(color, width);
		setBorderBottom(color, width);
		setBorderLeft(color, width);
		setBorderRight(color, width);
		return this;
	}

	public ACell setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
		return this;
	}

	public ACell setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public ACell setPaddingTop(float paddingTop) {
		this.paddingTop = paddingTop;
		return this;
	}

	public ACell setPaddingRight(float paddingRight) {
		this.paddingRight = paddingRight;
		return this;
	}

	public ACell setPaddingBottom(float paddingBottom) {
		this.paddingBottom = paddingBottom;
		return this;
	}

	public ACell setPaddingLeft(float paddingLeft) {
		this.paddingLeft = paddingLeft;
		return this;
	}

	public ACell setPadding(float padding) {
		setPaddingTop(padding);
		setPaddingRight(padding);
		setPaddingBottom(padding);
		setPaddingLeft(padding);
		return this;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getBorderBottomColor() {
		return borderBottomColor;
	}

	public float getBorderBottomWidth() {
		return borderBottomWidth;
	}

	public Color getBorderLeftColor() {
		return borderLeftColor;
	}

	public float getBorderLeftWidth() {
		return borderLeftWidth;
	}

	public Color getBorderRightColor() {
		return borderRightColor;
	}

	public float getBorderRightWidth() {
		return borderRightWidth;
	}

	public Color getBorderTopColor() {
		return borderTopColor;
	}

	public float getBorderTopWidth() {
		return borderTopWidth;
	}

	public float getPaddingBottom() {
		return paddingBottom;
	}

	public float getPaddingLeft() {
		return paddingLeft;
	}

	public float getPaddingRight() {
		return paddingRight;
	}

	public float getPaddingTop() {
		return paddingTop;
	}

	public FontStyle getFontStyle() {
		return fontStyle;
	}

	// --- dependencies ---

}
