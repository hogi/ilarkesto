package ilarkesto.pdf.itext;

import ilarkesto.pdf.AImage;
import ilarkesto.pdf.AParagraph;
import ilarkesto.pdf.APdfBuilder;
import ilarkesto.pdf.ATable;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

public class PdfBuilder extends APdfBuilder {

	public static void main(String[] args) throws Throwable {
		PdfBuilder pdf = new PdfBuilder();
		pdf.paragraph().setHeight(72).text("first");
		pdf.paragraph().setHeight(10).text("second");
		pdf.paragraph().setHeight(1);
		pdf.paragraph().text("--------------------------");
		ATable table = pdf.table(50, 50);
		table.cell().paragraph().text("1 ABC");
		table.cell().setBorder(Color.RED, 0.5f).paragraph().text("2 ABC\u00DC\u00DC\nABCDEF");
		table.cell().paragraph().text("3 ABC");
		table.cell().paragraph().text("4 ABC");
		pdf.write(new FileOutputStream("c:/tmp/test.pdf"));
	}

	private Collection<ItextElement> elements = new ArrayList<ItextElement>();

	public void write(File file) {
		file.getParentFile().mkdirs();
		try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			write(out);
			out.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void write(OutputStream out) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, out);
		} catch (DocumentException ex) {
			throw new RuntimeException(ex);
		}
		document.setMargins(mmToPoints(marginLeft), mmToPoints(marginRight), mmToPoints(marginTop),
			mmToPoints(marginBottom));
		document.open();
		for (ItextElement element : elements) {
			try {
				document.add(element.getITextElement());
			} catch (DocumentException ex) {
				throw new RuntimeException(ex);
			}
		}
		document.close();
	}

	@Override
	public AParagraph paragraph() {
		Paragraph p = new Paragraph(this);
		p.setDefaultFontStyle(defaultFontStyle);
		elements.add(p);
		return p;
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

	@Override
	public AImage image(File file) {
		Image i = new Image(this, file);
		elements.add(i);
		return i;
	}

}
