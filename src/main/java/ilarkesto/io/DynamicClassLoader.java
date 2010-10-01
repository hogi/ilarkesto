package ilarkesto.io;

import ilarkesto.core.logging.Log;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DynamicClassLoader extends ClassLoader {

	private static final Log LOG = Log.get(DynamicClassLoader.class);

	private String[] packages;
	private Map<String, Long> typeModificationTimes = new HashMap<String, Long>();

	public DynamicClassLoader(ClassLoader parent, String... packages) {
		super(parent);
		this.packages = packages;
		if (packages.length == 0) throw new IllegalArgumentException("At least one package required");
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		for (String pack : packages) {
			if (name.startsWith(pack)) {
				String typeName = name.replace('.', '/') + ".class";
				URL url = getParent().getResource(typeName);
				String fileName = url.getFile();
				File file = new File(fileName);
				if (!file.exists()) throw new RuntimeException("File does not exist: " + file.getPath());

				// Long lastModified = typeModificationTimes.get(name);
				// if (lastModified != null && lastModified == file.lastModified()) {
				//					
				// }
				//
				// typeModificationTimes.put(name, file.lastModified());

				LOG.debug("Defining class:", name);
				byte[] data = IO.readFileToByteArray(file);
				Class<?> type = defineClass(name, data, 0, data.length);
				resolveClass(type);
				return type;
			}
		}
		return super.loadClass(name);
	}

}
