package ilarkesto.scm;

import java.io.File;

public abstract class AScmTool {

	public abstract String getName();

	public abstract String getVersion();

	public abstract boolean isProjectDir(File dir);

	protected abstract AScmProject createProject(File dir);

	public AScmProject getProject(File projectDir) {
		if (!projectDir.exists()) throw new RuntimeException("Project does not exist: " + projectDir.getPath());
		if (!isProjectDir(projectDir))
			throw new RuntimeException("Not a " + getName() + " project: " + projectDir.getPath());
		return createProject(projectDir);
	}

	public boolean isAvailable() {
		try {
			getVersion();
		} catch (Throwable ex) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getName();
	}

}
