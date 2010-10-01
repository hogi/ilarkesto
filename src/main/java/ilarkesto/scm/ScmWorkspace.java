package ilarkesto.scm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScmWorkspace {

	private File dir;
	private Collection<AScmTool> tools;

	public ScmWorkspace(File dir, Collection<AScmTool> tools) {
		this.dir = dir;
		this.tools = tools;
	}

	public List<AScmProject> getProjects() {
		List<AScmProject> ret = new ArrayList<AScmProject>();
		File[] dirs = dir.listFiles();
		if (dirs == null || dirs.length == 0) return ret;
		for (File dir : dirs) {
			if (!dir.isDirectory()) continue;
			for (AScmTool tool : tools) {
				if (tool.isProjectDir(dir)) {
					ret.add(tool.getProject(dir));
				}
			}
		}
		return ret;
	}

	public File getDir() {
		return dir;
	}

	@Override
	public String toString() {
		return "ScmWorkspace(" + dir.getPath() + ")";
	}

}
