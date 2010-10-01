package ilarkesto.albumartorg;

import ilarkesto.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;

import java.io.File;
import java.io.FileNotFoundException;

public class AlbumArt {

	private static Log log = Log.get(AlbumArt.class);

	public static String determineCoverUrl(String title) {
		log.info("Determining cover for:", title);
		String html = IO
				.downloadUrlToString("http://www.albumart.org/index.php?itempage=1&newsearch=1&searchindex=Music&srchkey="
						+ Str.encodeUrlParameter(title));
		if (Str.isBlank(html)) return null;
		String coverId = Str.cutFromTo(html, "<a href=\"http://ecx.images-amazon.com/images/I/", "\"");
		if (coverId == null) return null;
		return "http://ecx.images-amazon.com/images/I/" + coverId;
	}

	public static boolean downloadCover(String title, File destinationFile) {
		String url = determineCoverUrl(title);
		if (url == null) return false;
		log.info("Downloading cover:", url);
		try {
			IO.downloadUrlToFile(url, destinationFile.getPath());
		} catch (Exception ex) {
			if (ex instanceof FileNotFoundException) return false;
			log.info("Downloading cover image failed:", url, ex);
			return false;
		}
		return true;
	}

}
