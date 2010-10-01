package ilarkesto.pdf;

import java.awt.Color;

public abstract class ATable extends APdfElement {

	private Float width = 100f;
	private float[] cellWidths;
	private int columnCount;

	private Float defaultCellPadding;

	public abstract ACell cell();

	public abstract ARow row();

	public abstract ARow row(Object... cellTexts);

	public abstract ATable createCellBorders(Color color, float width);

	/**
	 * Width in percent.
	 */
	public ATable setWidth(Float width) {
		this.width = width;
		return this;
	}

	public Float getWidth() {
		return width;
	}

	public float[] getCellWidths() {
		return cellWidths;
	}

	public ATable setCellWidths(float... cellWidths) {
		this.cellWidths = cellWidths;
		setColumnCount(cellWidths.length);
		return this;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public ATable setColumnCount(int columnCount) {
		this.columnCount = columnCount;
		return this;
	}

	public ATable setDefaultCellPadding(Float defaultCellPadding) {
		this.defaultCellPadding = defaultCellPadding;
		return this;
	}

	public Float getDefaultCellPadding() {
		return defaultCellPadding;
	}

	// --- helper ---

	public ACell cell(Object text) {
		ACell cell = cell();
		if (text != null) cell.paragraph().text(text);
		return cell;
	}

	public ACell cell(Object text, FontStyle fontStyle) {
		ACell cell = cell();
		if (text != null) cell.paragraph().text(text, fontStyle);
		return cell;
	}

	// --- dependencies ---

	public ATable(APdfElement parent) {
		super(parent);
	}

}
