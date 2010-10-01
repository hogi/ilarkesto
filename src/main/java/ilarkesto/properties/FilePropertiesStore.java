package ilarkesto.properties;

import ilarkesto.base.Sys;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;

import java.io.File;
import java.util.Properties;

public class FilePropertiesStore extends APropertiesStore {

	private static final Log LOG = Log.get(FilePropertiesStore.class);
	private static final String CHARSET = IO.UTF_8;

	@Override
	protected Properties load() {
		Properties p = new Properties();
		if (file.exists()) {
			p = IO.loadProperties(file, CHARSET);
			LOG.info("Loaded properties:", file);
		}
		return p;
	}

	@Override
	protected void save(Properties properties) {
		IO.saveProperties(properties, getClass().getSimpleName(), file);
	}

	@Override
	public String toString() {
		return file.getPath();
	}

	// --- dependencies ---

	private File file;

	public FilePropertiesStore(File file, boolean createFileIfNotExists) {
		this.file = file;
		if (createFileIfNotExists && !file.exists()) {
			IO.touch(file);
			LOG.info("Properties file created:", file.getPath());
		}
	}

	public FilePropertiesStore(String path, boolean createFileIfNotExists) {
		this(new File(path), createFileIfNotExists);
	}

	/**
	 * Creates a properties file for the given class in the users home directory.
	 */
	public FilePropertiesStore(String appName, Class clazz, boolean createFileIfNotExists) {
		this(Sys.getUsersHomePath() + "/." + appName + "/" + clazz.getSimpleName() + ".properties",
				createFileIfNotExists);
	}

}
