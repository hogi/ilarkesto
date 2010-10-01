package ilarkesto.pdf;

public class FieldList extends APdfElement {

	private ATable table;
	private FontStyle labelFontStyle;

	public FieldList(APdfContainerElement parent) {
		super(parent);
		table = parent.table(1, 4);
	}

	public APdfContainerElement field(String label) {
		ARow row = table.row();
		row.cell(label, labelFontStyle);
		return row.cell();
	}

	public FieldList setLabelFontStyle(FontStyle labelFontStyle) {
		this.labelFontStyle = labelFontStyle;
		return this;
	}
}
