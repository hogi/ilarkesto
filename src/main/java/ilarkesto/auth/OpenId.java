package ilarkesto.auth;

import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.Parameter;
import org.openid4java.message.ParameterList;
import org.openid4java.util.HttpClientFactory;
import org.openid4java.util.ProxyProperties;

/**
 * http://code.google.com/p/openid4java/
 */
public class OpenId {

	public static final String MYOPENID = "http://myopenid.com/";
	public static final String GOOGLE = "https://www.google.com/accounts/o8/id";
	public static final String YAHOO = "https://me.yahoo.com/";
	public static final String LAUNCHPAD = "http://login.launchpad.net";
	public static final String VERISIGN = "https://pip.verisignlabs.com/";
	public static final String BLOGSPOT = "https://www.blogspot.com/";
	public static final String AOL = "http://openid.aol.com/";
	public static final String FLICKR = "http://www.flickr.com/";
	public static final String MYVIDOOP = "https://myvidoop.com/";
	public static final String WORDPRESS = "https://wordpress.com/";

	public static final String LIVEJOURNAL_TEMPLATE = "http://${username}.livejournal.com/";
	public static final String CLAIMID_TEMPLATE = "https://claimid.com/$(username)";
	public static final String TECHNORATI_TEMPLATE = "https://technorati.com/people/technorati/$(username)/";

	private static Log log = Log.get(OpenId.class);

	public static String cutUsername(String openId) {
		if (openId == null) return null;
		String name = openId;
		if (name.startsWith(GOOGLE + "?id=")) return Str.cutFrom(name, "=");
		if (name.startsWith(YAHOO)) return Str.cutFrom(name, ".com/");
		if (name.startsWith("https://login.launchpad.net/+id/")) return Str.cutFrom(name, "+id/");
		if (name.startsWith("https://") && name.endsWith(".pip.verisignlabs.com/"))
			return Str.cutFromTo(name, "//", ".pip");
		if (name.startsWith("http://openid.aol.com/")) return Str.cutFrom(name, ".com/");
		if (name.startsWith("https://") && name.endsWith(".myvidoop.com/"))
			return Str.cutFromTo(name, "//", ".myvidoop");
		if (name.contains("/")) name = Str.cutFrom(name, "/");
		if (name.endsWith(".myopenid.com/")) name = Str.cutTo(name, ".");
		return name;
	}

	public static boolean isOpenIdCallback(HttpServletRequest request) {
		if (request.getParameter("openid.ns") != null) return true;
		if (request.getParameter("openid.identity") != null) return true;
		return false;
	}

	public static String createAuthenticationRequestUrl(String openId, String returnUrl, HttpSession session)
			throws RuntimeException {
		try {
			ConsumerManager manager = getConsumerManager(session);
			List discoveries = manager.discover(openId);
			DiscoveryInformation discovered = manager.associate(discoveries);
			session.setAttribute("openIdDiscovered", discovered);
			AuthRequest authReq = manager.authenticate(discovered, returnUrl);
			return authReq.getDestinationUrl(true);
		} catch (Exception ex) {
			throw new RuntimeException("Creating OpenID authentication request URL failed.", ex);
		}
	}

	public static String getIdentifierFromCallback(HttpServletRequest request) {
		log.info("Reading OpenID response");
		ParameterList openidResp = new ParameterList(request.getParameterMap());
		for (Iterator iterator = openidResp.getParameters().iterator(); iterator.hasNext();) {
			Parameter param = (Parameter) iterator.next();
			log.info("   ", param.getKey(), "->", param.getValue());
		}
		HttpSession session = request.getSession();
		DiscoveryInformation discovered = (DiscoveryInformation) session.getAttribute("openIdDiscovered");

		// extract the receiving URL from the HTTP request
		StringBuffer receivingURL = request.getRequestURL();
		String queryString = request.getQueryString();
		if (queryString != null && queryString.length() > 0) receivingURL.append("?").append(request.getQueryString());

		// verify the response
		VerificationResult verification;
		try {
			verification = getConsumerManager(session).verify(receivingURL.toString(), openidResp, discovered);
		} catch (Exception ex) {
			throw new RuntimeException("Reading OpenID response data failed.", ex);
		}

		Identifier verifiedId = verification.getVerifiedId();
		return verifiedId == null ? null : verifiedId.getIdentifier();
	}

	public static String getIdentifierFromCallbackWithoutSuffix(HttpServletRequest request) {
		String id = getIdentifierFromCallback(request);
		if (id == null || !id.contains("#")) return id;
		return Str.cutTo(id, "#");
	}

	public static ConsumerManager getConsumerManager(HttpSession session) {
		String sessionAttribute = "openIdConsumerManager";
		ConsumerManager manager = (ConsumerManager) session.getAttribute(sessionAttribute);
		if (manager == null) {
			try {
				manager = new ConsumerManager();
			} catch (ConsumerException ex) {
				throw new RuntimeException("Creating OpenID ConsumerManager failed.", ex);
			}
			session.setAttribute(sessionAttribute, manager);
		}
		return manager;
	}

	public static void setHttpProxy(String hostname, int port) {
		ProxyProperties proxyProps = new ProxyProperties();
		proxyProps.setProxyHostName(hostname);
		proxyProps.setProxyPort(port);
		HttpClientFactory.setProxyProperties(proxyProps);
	}

}
