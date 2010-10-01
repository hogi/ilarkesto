package ilarkesto.scm.svn;

import ilarkesto.base.Proc;
import ilarkesto.base.Str;
import ilarkesto.scm.AScmTool;

import java.io.File;

public class Svn extends AScmTool {

	public static final Svn THIS = new Svn();

	private String executable = "svn";

	String exec(File workDir, String... parameters) {
		Proc proc = new Proc(executable);
		proc.addParameters(parameters);
		proc.setWorkingDir(workDir);
		proc.addEnvironmentParameter("LANG", "en_US.UTF-8");
		return proc.execute();
	}

	@Override
	public String getVersion() {
		String output = exec(null, "--version");
		output = Str.getFirstLine(output);
		output = output.substring(13, output.lastIndexOf(' '));
		return output;
	}

	@Override
	protected SvnProject createProject(File dir) {
		return new SvnProject(this, dir);
	}

	@Override
	public String getName() {
		return "svn";
	}

	@Override
	public boolean isProjectDir(File dir) {
		File svnDir = new File(dir.getPath() + "/.svn");
		return svnDir.exists() && svnDir.isDirectory();
	}

	public void setExecutable(String executable) {
		this.executable = executable;
	}
}
