package ilarkesto.pdf.itext;

import ilarkesto.pdf.ACell;
import ilarkesto.pdf.AImage;
import ilarkesto.pdf.AParagraph;
import ilarkesto.pdf.APdfBuilder;
import ilarkesto.pdf.APdfElement;
import ilarkesto.pdf.ATable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPCell;

public class Cell extends ACell implements ItextElement {

	private Collection<ItextElement> elements = new ArrayList<ItextElement>();

	public Cell(APdfElement parent) {
		super(parent);
	}

	@Override
	public AParagraph paragraph() {
		Paragraph p = new Paragraph(this);
		p.setDefaultFontStyle(getFontStyle());
		elements.add(p);
		return p;
	}

	@Override
	public AImage image(File file) {
		Image i = new Image(this, file);
		elements.add(i);
		return i;
	}

	@Override
	public ATable table(float... cellWidths) {
		Table t = new Table(this);
		t.setCellWidths(cellWidths);
		elements.add(t);
		return t;
	}

	@Override
	public ATable table(int columnCount) {
		Table t = new Table(this);
		t.setColumnCount(columnCount);
		elements.add(t);
		return t;
	}

	@Override
	public AImage image(byte[] data) {
		Image i = new Image(this, data);
		elements.add(i);
		return i;
	}

	public Element getITextElement() {
		PdfPCell cell = new PdfPCell();
		cell.setBorderColorTop(getBorderTopColor());
		cell.setBorderColorBottom(getBorderBottomColor());
		cell.setBorderColorLeft(getBorderLeftColor());
		cell.setBorderColorRight(getBorderRightColor());
		cell.setBorderWidthTop(APdfBuilder.mmToPoints(getBorderTopWidth()));
		cell.setBorderWidthBottom(APdfBuilder.mmToPoints(getBorderBottomWidth()));
		cell.setBorderWidthLeft(APdfBuilder.mmToPoints(getBorderLeftWidth()));
		cell.setBorderWidthRight(APdfBuilder.mmToPoints(getBorderRightWidth()));
		cell.setPadding(0);
		cell.setPaddingTop(APdfBuilder.mmToPoints(getPaddingTop()));
		cell.setPaddingBottom(APdfBuilder.mmToPoints(getPaddingBottom()));
		cell.setPaddingLeft(APdfBuilder.mmToPoints(getPaddingLeft()));
		cell.setPaddingRight(APdfBuilder.mmToPoints(getPaddingRight()));
		cell.setExtraParagraphSpace(0);
		cell.setIndent(0);
		cell.setUseBorderPadding(false);
		cell.setBackgroundColor(getBackgroundColor());
		for (ItextElement element : elements)
			cell.addElement(element.getITextElement());
		return cell;
	}

	// --- dependencies ---

}
