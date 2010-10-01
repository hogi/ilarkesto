package ilarkesto.pdf.itext;

import ilarkesto.pdf.AImage;
import ilarkesto.pdf.AParagraph;
import ilarkesto.pdf.AParagraphElement;
import ilarkesto.pdf.APdfElement;
import ilarkesto.pdf.FontStyle;
import ilarkesto.pdf.TextChunk;

import java.awt.Color;
import java.io.File;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class Paragraph extends AParagraph implements ItextElement {

	public Paragraph(APdfElement parent) {
		super(parent);
	}

	public Element getITextElement() {
		com.lowagie.text.Paragraph p = new com.lowagie.text.Paragraph();
		float maxSize = 0;
		for (AParagraphElement element : getElements()) {
			if (element instanceof TextChunk) {
				TextChunk textChunk = (TextChunk) element;
				Chunk chunk = new Chunk(textChunk.getText());
				FontStyle style = textChunk.getFontStyle();
				Font font;
				try {
					font = new Font(BaseFont.createFont(style.getFont(), BaseFont.CP1252, BaseFont.EMBEDDED));
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
				if (style.isItalic() && style.isBold()) {
					font.setStyle(Font.BOLDITALIC);
				} else if (style.isItalic()) {
					font.setStyle(Font.ITALIC);
				} else if (style.isBold()) {
					font.setStyle(Font.BOLD);
				}
				font.setSize(PdfBuilder.mmToPoints(style.getSize()));
				Color color = style.getColor();
				if (color != null) font.setColor(color);
				chunk.setFont(font);
				p.add(chunk);
				float size = style.getSize() * 1.3f;
				if (size > maxSize) maxSize = PdfBuilder.mmToPoints(size);
			} else if (element instanceof Image) {
				Image image = (Image) element;
				com.lowagie.text.Image itextImage = image.getITextElement();

				if (image.getAlign() != null) {
					itextImage.setAlignment(Image.convertAlign(image.getAlign()) | com.lowagie.text.Image.TEXTWRAP);
					p.add(itextImage);
				} else {
					Chunk chunk = new Chunk(itextImage, 0, 0);
					p.add(chunk);
					float size = image.getHeight() + 3;
					if (size > maxSize) maxSize = size;
				}

			} else {
				throw new RuntimeException("Unsupported paragraph element: " + element.getClass().getName());
			}
		}
		p.setLeading(maxSize);
		if (align != null) p.setAlignment(convertAlign(align));
		if (height <= 0) return p;

		// wrap in table
		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setFixedHeight(PdfBuilder.mmToPoints(height));
		cell.addElement(p);
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.addCell(cell);
		return table;
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

	private static int convertAlign(Align align) {
		switch (align) {
			case RIGHT:
				return com.lowagie.text.Paragraph.ALIGN_RIGHT;
		}
		throw new RuntimeException("Unsupported align: " + align);
	}

	// --- dependencies ---

}
