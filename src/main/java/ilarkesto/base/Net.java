package ilarkesto.base;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Utilities for networking.
 */
public abstract class Net {

	public static final String getHostname(String ip) {
		String host = null;
		try {
			for (InetAddress a : InetAddress.getAllByName(ip)) {
				host = a.getHostName();
				if (host.length() > 0 && !Character.isDigit(host.charAt(0))) break;
			}
		} catch (UnknownHostException ex) {}
		return host;
	}

	public static String getHostnameOrIp(String ip) {
		String host = getHostname(ip);
		return host == null ? ip : host;
	}

}
