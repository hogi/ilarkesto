package ilarkesto.pdf;

import java.io.File;

public abstract class AImage extends AParagraphElement {

	public enum Align {
		LEFT, RIGHT
	}

	protected byte[] data;
	protected File file;
	protected Float scaleByHeight;
	protected Float scaleByWidth;
	protected Align align;

	public AImage(APdfElement parent, byte[] data) {
		super(parent);
		this.data = data;
	}

	public AImage(APdfElement parent, File file) {
		super(parent);
		this.file = file;
	}

	public Align getAlign() {
		return align;
	}

	// --- helper ---

	public AImage setAlignLeft() {
		return setAlign(Align.LEFT);
	}

	public AImage setAlignRight() {
		return setAlign(Align.RIGHT);
	}

	// --- dependencies ---

	public AImage setScaleByHeight(Float scaleByHeight) {
		this.scaleByHeight = scaleByHeight;
		return this;
	}

	public AImage setScaleByWidth(Float scaleByWidth) {
		this.scaleByWidth = scaleByWidth;
		return this;
	}

	public AImage setAlign(Align align) {
		this.align = align;
		return this;
	}

}
