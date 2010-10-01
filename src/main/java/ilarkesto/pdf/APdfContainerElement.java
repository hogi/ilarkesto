package ilarkesto.pdf;

import java.io.File;

public abstract class APdfContainerElement extends APdfElement {

	public abstract AParagraph paragraph();

	public abstract AImage image(File file);

	public abstract AImage image(byte[] data);

	public abstract ATable table(float... cellWidths);

	public abstract ATable table(int columnCount);

	public APdfContainerElement(APdfElement parent) {
		super(parent);
	}

	public FieldList fieldList() {
		return new FieldList(this);
	}

	public APdfContainerElement text(Object text) {
		paragraph().text(text);
		return this;
	}

	public APdfContainerElement nl(FontStyle fontStyle) {
		paragraph().nl(fontStyle);
		return this;
	}

	public APdfContainerElement nl() {
		paragraph().nl();
		return this;
	}

}
