package ilarkesto.webapp;

import ilarkesto.base.Net;
import ilarkesto.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Servlet {

	private static final Log LOG = Log.get(Servlet.class);

	public static final String ENCODING = IO.UTF_8;

	private Servlet() {}

	public static String getWebappUrl(ServletConfig servletConfig, boolean ssl) {
		String protocol = ssl ? "https" : "http";
		String host = IO.getHostName();
		String context = servletConfig.getServletContext().getServletContextName();
		return protocol + "://" + host + "/" + context;
	}

	public static void preventCaching(HttpServletResponse httpResponse) {
		// prevent caching HTTP 1.1
		httpResponse.setHeader("Cache-Control", "no-cache");

		// prevent caching HTTP 1.0
		httpResponse.setHeader("Pragma", "no-cache");

		// prevent caching at the proxy server
		httpResponse.setDateHeader("Expires", 0);
	}

	public static void serveFile(File file, HttpServletResponse httpResponse) throws IOException {
		httpResponse.setContentType("application/octet-stream");
		httpResponse.setContentLength((int) file.length());
		Servlet.setFilename(file.getName(), httpResponse);
		IO.copyFile(file, httpResponse.getOutputStream());
	}

	public static void setFilename(String fileName, HttpServletResponse httpResponse) {
		httpResponse.setHeader("Content-Disposition", "inline; filename=" + fileName + ";");
	}

	public static final String getContextPath(ServletConfig servletConfig) {
		return getContextPath(servletConfig.getServletContext());
	}

	public static final String getContextPath(ServletContext servletContext) {
		String realPath = servletContext.getRealPath("dummy");
		LOG.info("servletContextName:", servletContext.getServletContextName());
		LOG.info("!!! dummy real path:", realPath);
		File file = new File(realPath);
		String path = file.getParentFile().getName();

		// TODO String path = servletContext.getContextPaht() when servlet-2.5

		if (path == null) return null;
		path = path.trim();
		if (path.startsWith("/")) path = path.substring(1);
		if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
		path = path.trim();
		if (path.length() == 0) return null;
		if (path.equals("ROOT")) return null;
		return path;
	}

	public static final String getUriWithoutContextWithParameters(HttpServletRequest httpRequest) {
		StringBuilder sb = new StringBuilder();
		sb.append(getUriWithoutContext(httpRequest));
		sb.append("?");
		Enumeration e = httpRequest.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			sb.append(parameter);
			sb.append('=');
			sb.append(httpRequest.getParameter(parameter));
			sb.append("&");
		}
		return sb.toString();
	}

	public static final String getUriWithoutContext(HttpServletRequest httpRequest) {
		String uri = httpRequest.getRequestURI();
		String context = httpRequest.getContextPath();
		if (uri.length() <= context.length() + 1) return "";
		return uri.substring(context.length() + 1);
	}

	public static String getRemoteHost(HttpServletRequest r) {
		return Net.getHostnameOrIp(r.getRemoteAddr());
	}

	public static String getUserAgent(HttpServletRequest r) {
		return r.getHeader("User-Agent");
	}

	public static String toString(HttpServletRequest r, String indent) {
		StringBuilder sb = new StringBuilder();
		sb.append(indent).append("requestedURL:       ").append(r.getRequestURL()).append("\n");
		sb.append(indent).append("requestedURI:       ").append(r.getRequestURI()).append("\n");
		sb.append(indent).append("queryString:        ").append(r.getQueryString()).append("\n");
		sb.append(indent).append("contextPath:        ").append(r.getContextPath()).append("\n");
		sb.append(indent).append("pathInfo:           ").append(r.getPathInfo()).append("\n");
		sb.append(indent).append("pathTranslated:     ").append(r.getPathTranslated()).append("\n");
		sb.append(indent).append("parameters:         ").append(Str.format(r.getParameterMap())).append("\n");
		sb.append(indent).append("headers:            ").append(Str.format(getHeaders(r))).append("\n");
		sb.append(indent).append("attributes:         ").append(Str.format(getAttributes(r))).append("\n");
		sb.append(indent).append("cookies:            ").append(Str.format(r.getCookies())).append("\n");
		sb.append(indent).append("protocol:           ").append(r.getProtocol()).append("\n");
		sb.append(indent).append("method:             ").append(r.getMethod()).append("\n");
		sb.append(indent).append("scheme:             ").append(r.getScheme()).append("\n");
		sb.append(indent).append("contentType:        ").append(r.getContentType()).append("\n");
		sb.append(indent).append("contentLenght:      ").append(r.getContentLength()).append("\n");
		sb.append(indent).append("characterEncoding:  ").append(r.getCharacterEncoding()).append("\n");
		sb.append(indent).append("authType:           ").append(r.getAuthType()).append("\n");
		sb.append(indent).append("CLIENT_CERT_AUTH:   ").append(r.getHeader(HttpServletRequest.CLIENT_CERT_AUTH))
				.append("\n");
		sb.append(indent).append("DIGEST_AUTH:        ").append(r.getHeader(HttpServletRequest.DIGEST_AUTH))
				.append("\n");
		sb.append(indent).append("remoteUser:         ").append(r.getRemoteUser()).append("\n");
		sb.append(indent).append("remoteAddr:         ").append(r.getRemoteAddr()).append("\n");
		sb.append(indent).append("remoteHost:         ").append(r.getRemoteHost()).append("\n");
		sb.append(indent).append("remotePort:         ").append(r.getRemotePort()).append("\n");
		sb.append(indent).append("requestedSessionId: ").append(r.getRequestedSessionId()).append("\n");
		sb.append(indent).append("secure:             ").append(r.isSecure()).append("\n");
		sb.append(indent).append("locale:             ").append(r.getLocale()).append("\n");
		sb.append(indent).append("locales:            ").append(Str.format(r.getLocales())).append("\n");
		sb.append(indent).append("localName:          ").append(r.getLocalName()).append("\n");
		sb.append(indent).append("localPort:          ").append(r.getLocalPort()).append("\n");
		sb.append(indent).append("localAddr:          ").append(r.getLocalAddr()).append("\n");
		sb.append(indent).append("serverName:         ").append(r.getServerName()).append("\n");
		sb.append(indent).append("serverPort:         ").append(r.getServerPort()).append("\n");
		sb.append(indent).append("servletPath:        ").append(r.getServletPath()).append("\n");
		return sb.toString();
	}

	public static Map<String, String> getHeaders(HttpServletRequest r) {
		Map<String, String> result = new HashMap<String, String>();
		Enumeration names = r.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = r.getHeader(name);
			result.put(name, value);
		}
		return result;
	}

	public static Map<String, Object> getAttributes(HttpServletRequest r) {
		Map<String, Object> result = new HashMap<String, Object>();
		Enumeration names = r.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			Object value = r.getAttribute(name);
			result.put(name, value);
		}
		return result;
	}

	public static void removeCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(1);
		response.addCookie(cookie);
	}

	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue,
			int maxAgeInSeconds) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAgeInSeconds);
		response.addCookie(cookie);
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie == null) return null;
		return cookie.getValue();
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) return null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) return cookies[i];
		}
		return null;
	}

}
