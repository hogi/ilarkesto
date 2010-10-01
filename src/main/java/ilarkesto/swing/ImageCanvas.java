package ilarkesto.swing;

import ilarkesto.io.IO;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageCanvas extends Component {

	public static void main(String[] args) throws Throwable {
		ImageCanvas ic = new ImageCanvas();
		ic.setImage(Swing.captureScreen(Swing.getWindow(ic)));
		ic.setPreferredSize(new Dimension(300, 300));
		Swing.showInJFrame(ic, "ImageCanvas", null, true);
	}

	private Color backgroundColor = Color.DARK_GRAY;

	private BufferedImage image;
	private boolean autoScale;

	public ImageCanvas() {}

	public ImageCanvas(BufferedImage image) {
		setImage(image);
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		if (image != null) {
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		}
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);

		if (image == null) return;

		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		if (autoScale) {
			if (imageWidth > width || imageHeight > height) {
				image = IO.toBufferedImage(IO.getScaled(this.image, width, height));
				imageWidth = image.getWidth();
				imageHeight = image.getHeight();
			}
		}

		int x = 0;
		int y = 0;

		if (width > imageWidth) x = (width - imageWidth) / 2;
		if (height > imageHeight) y = (height - imageHeight) / 2;

		g.drawImage(image, x, y, null);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setAutoScale(boolean autoScale) {
		this.autoScale = autoScale;
	}

	public boolean isAutoScale() {
		return autoScale;
	}
}
