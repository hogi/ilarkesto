package ilarkesto.scm;

import java.io.File;

public abstract class AScmProject {

	private AScmTool tool;
	private File dir;

	public abstract boolean pullFromOrigin();

	public abstract String getVersion();

	public abstract String getLogAndDiffSince(String version);

	public abstract boolean isDirty();

	public AScmProject(AScmTool tool, File dir) {
		this.tool = tool;
		this.dir = dir;
	}

	public final File getDir() {
		return dir;
	}

	public AScmTool getTool() {
		return tool;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + dir.getPath() + ")";
	}

}
