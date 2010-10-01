package ilarkesto.persistence;

import ilarkesto.io.StringOutputStream;
import ilarkesto.io.IO.StringInputStream;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Serializer {

	public abstract void setAlias(String alias, Class clazz);

	public abstract void serialize(Object bean, OutputStream out);

	public abstract Object deserialize(InputStream in);

	public final Object deserialize(String s) {
		if (s == null || s.length() == 0) return null;
		StringInputStream in = new StringInputStream(s);
		return deserialize(in);
	}

	public final String serializeToString(Object bean) {
		StringOutputStream out = new StringOutputStream();
		serialize(bean, out);
		return out.toString();
	}
}
