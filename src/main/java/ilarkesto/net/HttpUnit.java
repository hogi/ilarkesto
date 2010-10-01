package ilarkesto.net;

import org.xml.sax.SAXException;

import com.meterware.httpunit.ClientProperties;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class HttpUnit {

	public static HTMLElement getFirstElementWithAttribute(WebResponse response, String name, String value) {
		HTMLElement[] elements;
		try {
			elements = response.getElementsWithAttribute("class", "photo");
		} catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
		return elements == null || elements.length < 1 ? null : elements[0];
	}

	public static WebResponse loadPage(String url) {
		try {
			return createWebConversation(false).getResponse(url);
		} catch (Exception ex) {
			throw new RuntimeException("Loading URL failed: " + url, ex);
		}
	}

	public static WebConversation createWebConversation(boolean acceptCookies) {
		HttpUnitOptions.setScriptingEnabled(false);
		WebConversation webConversation = new WebConversation();
		ClientProperties props = webConversation.getClientProperties();
		props.setAcceptGzip(false);
		props.setAcceptCookies(acceptCookies);
		props.setAutoRedirect(false);
		return webConversation;
	}

}
