package ilarkesto.tools.mkproject;

import java.io.File;

/**
 * Command line tool for creating a java-project. Prepared for Subversion, Maven and Eclipse.
 * 
 * @author wko
 */
public class MkProject {

	public static void main(String[] args) {
		if (args.length != 1) throw new RuntimeException("A project name must be specified as parameter.");

		String name = args[0];
		File dir = new File(name);

		mkdir(name);

		mkdir(name + "/tags");
		mkdir(name + "/branches");
		String trunkPath = name + "/trunk";
		mkdir(trunkPath);

		mkdir(trunkPath + "/src/main/java");
		mkdir(trunkPath + "/src/main/resources");
		mkdir(trunkPath + "/src/test/java");
		mkdir(trunkPath + "/src/test/resources");
		mkdir(trunkPath + "/target/classes");
		mkdir(trunkPath + "/target/test-classes");
	}

	private static void mkdir(String path) {
		File dir = new File(path);
		if (dir.exists() && dir.isDirectory()) return;
		if (!dir.mkdirs()) throw new RuntimeException("Failed to create dir: " + dir.getPath());
	}

	// --- dependencies ---

}
