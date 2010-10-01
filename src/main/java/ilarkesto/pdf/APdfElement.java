package ilarkesto.pdf;

public class APdfElement {

	protected APdfElement parent;

	public APdfElement(APdfElement parent) {
		this.parent = parent;
	}

	protected APdfBuilder getPdf() {
		if (parent instanceof APdfBuilder) return (APdfBuilder) parent;
		return parent.getPdf();
	}
}
