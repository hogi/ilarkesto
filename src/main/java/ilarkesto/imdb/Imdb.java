package ilarkesto.imdb;

import ilarkesto.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;
import ilarkesto.net.HttpUnit;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

public class Imdb {

	private static final String TITLE_URL_PREFIX = "http://www.imdb.com/title/";

	private static Log log = Log.get(Imdb.class);

	public static String determineIdByTitle(String title, boolean guess) {
		log.info("Determining IMDB-ID by title:", title);
		WebResponse response = HttpUnit.loadPage(getTitleSearchUrl(title));
		String url = response.getHeaderField("LOCATION");
		if (!Str.isBlank(url) && url.startsWith(TITLE_URL_PREFIX)) {
			url = Str.removePrefix(url, TITLE_URL_PREFIX);
			if (url.contains("/")) url = Str.cutTo(url, "/");
			return url;
		}

		if (guess) {
			WebLink[] links;
			try {
				links = response.getLinks();
			} catch (SAXException ex) {
				throw new RuntimeException(ex);
			}
			for (WebLink link : links) {
				String linkUrl = link.getURLString();
				if (Str.isBlank(linkUrl)) continue;
				linkUrl = Str.removePrefix(linkUrl, "http://www.imdb.com");
				if (linkUrl.startsWith("/title/")) {
					String id = Str.removePrefix(linkUrl, "/title/");
					return Str.removeSuffix(id, "/");
				}
			}
		}

		return null;
	}

	public static ImdbRecord loadRecord(String imdbId) {
		if (imdbId == null) return null;
		String url = getPageUrl(imdbId);
		log.info("Loading IMDB record:", imdbId);
		WebResponse response = HttpUnit.loadPage(url);
		String title = parseTitle(response);
		Integer year = parseYear(response);
		String coverId = parseCoverId(response);
		String tagline = parseInfoContent(response, "Tagline");
		String plot = parseInfoContent(response, "Plot");
		String awards = parseInfoContent(response, "Awards");

		return new ImdbRecord(imdbId, title, year, coverId);
	}

	private static String parseInfoContent(WebResponse response, String label) {
		String text;
		try {
			text = response.getText();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		text = Str.cutFromTo(text, "<h5>" + label + ":</h5>", "</div>");
		if (text == null) return null;
		text = Str.cutFrom(text, "<div class=\"info-content\">");
		if (text == null) return null;
		if (text.contains("<a ")) text = Str.cutTo(text, "<a ");
		return Str.html2text(text.trim());
	}

	private static Integer parseYear(WebResponse response) {
		String title;
		try {
			title = response.getTitle();
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
		if (title == null) return null;
		int idx = title.lastIndexOf(" (");
		if (idx < 1) return null;
		String year = title.substring(idx + 2, idx + 6);
		return Integer.parseInt(year);
	}

	private static String parseTitle(WebResponse response) {
		String title;
		try {
			title = response.getTitle();
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
		if (title == null) return null;
		int idx = title.indexOf(" (");
		if (idx < 1) return title;
		return title.substring(0, idx);
	}

	private static String parseCoverId(WebResponse response) {
		HTMLElement img;
		try {
			img = response.getElementWithID("primary-poster");
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
		if (img == null) return null;
		String url = img.getAttribute("src");
		if (url == null) return null;
		if (!url.startsWith("http://ia.media-imdb.com/images/M/")) return null;
		if (!url.contains("._")) return null;
		String id = Str.removePrefix(url, "http://ia.media-imdb.com/images/M/");
		id = id.substring(0, id.indexOf("._"));
		return id;
	}

	public static String getTitleSearchUrl(String title) {
		return "http://www.imdb.com/find?s=tt&q=" + Str.encodeUrlParameter(title);
	}

	public static String getPageUrl(String imdbId) {
		return TITLE_URL_PREFIX + imdbId + "/";
	}

	public static void downloadCover(String coverId, File destinationFile) {
		String url = getCoverUrl(coverId);
		log.info("Downloading IMDB cover:", url);
		IO.downloadUrlToFile(url, destinationFile.getPath());
	}

	public static String getCoverUrl(String coverId) {
		if (coverId == null) return null;
		return "http://ia.media-imdb.com/images/M/" + coverId + "._V1._SX510_SY755_.jpg";
	}

	public static String extractId(String url) {
		if (Str.isBlank(url)) return null;
		String id = url;
		id = Str.removePrefix(id, TITLE_URL_PREFIX);
		id = Str.removePrefix(id, "http://www.imdb.de/title/");
		id = Str.removeSuffix(id, "/");
		return id;
	}

}
