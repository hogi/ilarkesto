package ilarkesto.properties;

import ilarkesto.base.Str;
import ilarkesto.base.Utl;
import ilarkesto.email.EmailAddress;

import java.io.File;
import java.util.List;
import java.util.Properties;

public abstract class APropertiesStore {

	private Properties properties;

	protected abstract Properties load();

	protected abstract void save(Properties properties);

	// --- String ---

	public final String get(String name, String defaultValue) {
		return getProperties().getProperty(name, defaultValue);
	}

	public final String get(String name) {
		return get(name, null);
	}

	public final void set(String name, String value) {
		String oldValue = get(name);
		if (Utl.equals(value, oldValue)) return;
		getProperties().setProperty(name, value);
		save(getProperties());
	}

	public void remove(String name) {
		getProperties().remove(name);
		save(getProperties());
	}

	// --- List<String> ---

	public final List<String> getList(String name, List<String> defaultValue) {
		String s = get(name);
		return s == null ? defaultValue : Utl.toList(Str.tokenize(s, ";"));
	}

	// --- int ---

	public final int getInt(String name, int defaultValue) {
		String s = get(name);
		return s == null ? defaultValue : Integer.parseInt(s);
	}

	// --- boolean ---

	public final boolean getBoolean(String name, boolean defaultValue) {
		String s = get(name);
		return s == null ? defaultValue : Boolean.parseBoolean(s);
	}

	// --- crypt ---

	public final String getCrypted(String name) {
		// TODO decrypt
		return get(name);
	}

	public final String getMandatoryCrypted(String name) {
		String s = getCrypted(name);
		if (s == null) throw new RuntimeException("Missing mandatory property '" + name + "' in " + this);
		return s;
	}

	public final void setCrypted(String name, String value) {
		// TODO encrypt
		set(name, value);
	}

	// --- File ---

	public final File getFile(String name) {
		return getFile(name, null);
	}

	public final File getFile(String name, File defaultValue) {
		String path = get(name);
		if (path == null) return defaultValue;
		return new File(path); // TODO cache file
	}

	public final File getMandatoryFile(String name) {
		return new File(getMandatory(name));
	}

	public final void set(String name, File value) {
		set(name, value.getPath());
	}

	// --- EmailAddress ---

	public final EmailAddress getEmailAddress(String name) {
		String s = get(name);
		return s == null ? null : new EmailAddress(s);
	}

	public final EmailAddress getMandatoryEmailAddress(String name) {
		return new EmailAddress(getMandatory(name));
	}

	public final String getMandatory(String name) {
		String value = get(name);
		if (value == null) throw new RuntimeException("Missing mandatory property '" + name + "' in " + this);
		return value;
	}

	// --- ---

	private Properties getProperties() {
		if (properties == null) {
			properties = load();
		}
		return properties;
	}

}
