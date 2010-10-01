package ilarkesto.io;

import ilarkesto.base.Assert;
import ilarkesto.core.logging.Log;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageAnalyzer {

	private static final Log LOG = Log.get(ImageAnalyzer.class);

	private BufferedImage image;

	public ImageAnalyzer(BufferedImage img) {
		this.image = img;
		LOG.debug(image);
	}

	public ImageAnalyzer(File f) {
		this(IO.loadImage(f));
	}

	public int findColorFromEast(int color, int x, int y) {
		for (; x >= 0; x--) {
			int c = image.getRGB(x, y);
			if (c == color) return x;
		}
		return -1;
	}

	public void assertColor(int x, int y, int color) {
		Assert.equal(image.getRGB(x, y), color);
	}

	public void assertWidth(int w) {
		Assert.equal(image.getWidth(), w);
	}

	public void assertHeight(int h) {
		Assert.equal(image.getHeight(), h);
	}

	public ImageAnalyzer getSubimage(int x, int y, int w, int h) {
		return new ImageAnalyzer(image.getSubimage(x, y, w, h));
	}

	public ImageAnalyzer getSubimageFromNorthEast(int width, int height) {
		int x = image.getWidth() - width;
		return getSubimage(x, 0, width, height);
	}

	public ImageAnalyzer getSubimageFromEast(int width) {
		int x = image.getWidth() - width;
		return getSubimage(x, 0, width, image.getHeight());
	}

	public ImageAnalyzer getSubimageFromWest(int width) {
		return getSubimage(0, 0, width, image.getHeight());
	}

	public BufferedImage getImage() {
		return image;
	}

}
