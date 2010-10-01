package ilarkesto.mda.legacy.generator;

import ilarkesto.io.FilenameComparator;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public class GwtImageBundleGenerator extends AClassGenerator {

	private String packageName;

	public GwtImageBundleGenerator(String packageName) {
		super();
		this.packageName = packageName;
	}

	@Override
	protected void writeContent() {
		File folder = new File("src/main/java/" + packageName.replace('.', '/'));
		File[] files = folder.listFiles();
		if (files == null) throw new RuntimeException("Can not read folder contents: " + folder.getAbsolutePath());
		Arrays.sort(files, new FilenameComparator());
		for (File file : files) {
			String name = file.getName();
			String nameLower = name.toLowerCase();
			if (nameLower.endsWith(".png") || nameLower.endsWith(".gif") || nameLower.endsWith(".jpg")) {
				writeImage(name);
			}
		}
	}

	private void writeImage(String fileName) {
		String name = fileName;
		int idx = name.lastIndexOf('.');
		if (idx > 0) {
			name = name.substring(0, idx);
		}
		ln();
		ln("    @Resource(value=\"" + fileName + "\")");
		ln("    " + AbstractImagePrototype.class.getName(), name + "();");
	}

	@Override
	protected String getSuperclass() {
		return ImageBundle.class.getName();
	}

	@Override
	protected Set<String> getImports() {
		Set<String> ret = new LinkedHashSet<String>(super.getImports());
		ret.add(com.google.gwt.user.client.ui.ImageBundle.class.getName());
		return ret;
	}

	@Override
	protected String getName() {
		return "GImageBundle";
	}

	@Override
	protected String getPackage() {
		return packageName;
	}

	@Override
	protected boolean isInterface() {
		return true;
	}

	@Override
	protected boolean isOverwrite() {
		return true;
	}

}
