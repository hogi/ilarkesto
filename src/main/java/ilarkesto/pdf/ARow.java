package ilarkesto.pdf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ARow {

	private List<ACell> cells = new ArrayList<ACell>();

	public ACell cell() {
		ACell cell = table.cell();
		cells.add(cell);
		return cell;
	}

	public ACell cell(Object text, FontStyle fontStyle) {
		ACell cell = table.cell(text, fontStyle);
		cells.add(cell);
		return cell;
	}

	public ACell cell(Object text) {
		ACell cell = table.cell(text);
		cells.add(cell);
		return cell;
	}

	public ARow setBorder(Color color, float width) {
		for (ACell cell : cells)
			cell.setBorder(color, width);
		return this;
	}

	public ARow setBorderTop(Color color, float width) {
		for (ACell cell : cells)
			cell.setBorderTop(color, width);
		return this;
	}

	public ARow setBorderBottom(Color color, float width) {
		for (ACell cell : cells)
			cell.setBorderBottom(color, width);
		return this;
	}

	public ARow setBorderLeft(Color color, float width) {
		for (ACell cell : cells)
			cell.setBorderLeft(color, width);
		return this;
	}

	public ARow setBorderRight(Color color, float width) {
		for (ACell cell : cells)
			cell.setBorderRight(color, width);
		return this;
	}

	// --- dependencies ---

	private ATable table;

	public ARow(ATable table) {
		this.table = table;
	}

}
