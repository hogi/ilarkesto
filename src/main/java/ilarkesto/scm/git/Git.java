package ilarkesto.scm.git;

import ilarkesto.base.Proc;
import ilarkesto.scm.AScmProject;
import ilarkesto.scm.AScmTool;

import java.io.File;

public class Git extends AScmTool {

	public static final Git THIS = new Git();

	private String executable = "git";

	String exec(File workDir, String... parameters) {
		Proc proc = new Proc(executable);
		proc.addParameters(parameters);
		proc.setWorkingDir(workDir);
		proc.addEnvironmentParameter("LANG", "en_US.UTF-8");
		return proc.execute(0, 1);
	}

	@Override
	protected AScmProject createProject(File dir) {
		return new GitProject(this, dir);
	}

	@Override
	public String getVersion() {
		String output = exec(null, "version");
		output = output.trim();
		return output.substring(output.lastIndexOf(' ')).trim();
	}

	@Override
	public String getName() {
		return "git";
	}

	@Override
	public boolean isProjectDir(File dir) {
		File svnDir = new File(dir.getPath() + "/.git");
		return svnDir.exists() && svnDir.isDirectory();
	}

	public void setExecutable(String executable) {
		this.executable = executable;
	}

}
