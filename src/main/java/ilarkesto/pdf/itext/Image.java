package ilarkesto.pdf.itext;

import ilarkesto.pdf.AImage;
import ilarkesto.pdf.APdfBuilder;
import ilarkesto.pdf.APdfElement;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.BadElementException;

public class Image extends AImage implements ItextElement {

	private Float height;

	public Image(APdfElement parent, byte[] data) {
		super(parent, data);
	}

	public Image(APdfElement parent, File file) {
		super(parent, file);
	}

	public com.lowagie.text.Image getITextElement() {
		com.lowagie.text.Image image;
		try {
			if (data != null) {
				image = com.lowagie.text.Image.getInstance(data);
			} else {
				image = com.lowagie.text.Image.getInstance(file.getPath());
			}
			if (scaleByHeight != null) {
				height = APdfBuilder.mmToPoints(scaleByHeight);
				float width = image.width() * height / image.height();
				image.scaleAbsolute(width, height);
			} else if (scaleByWidth != null) {
				float width = APdfBuilder.mmToPoints(scaleByWidth);
				height = image.height() * width / image.width();
				image.scaleAbsolute(width, height);
			} else {
				height = image.height();
			}
			image.setIndentationLeft(0);
			image.setIndentationRight(0);
			image.setSpacingAfter(0);
			image.setSpacingBefore(0);
		} catch (BadElementException ex) {
			throw new RuntimeException(ex);
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		image.setWidthPercentage(0.1f);

		if (align != null) image.setAlignment(convertAlign(align));

		return image;
	}

	public float getHeight() {
		if (height == null) {
			height = getITextElement().height();
		}
		return height;
	}

	public static int convertAlign(Align align) {
		switch (align) {
			case LEFT:
				return com.lowagie.text.Image.LEFT;
			case RIGHT:
				return com.lowagie.text.Image.RIGHT;
		}
		throw new RuntimeException("Unsupported align: " + align);
	}

	// --- dependencies ---

}
