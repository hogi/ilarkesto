package ilarkesto.scm.git;

import ilarkesto.scm.AScmProject;

import java.io.File;

public class GitProject extends AScmProject {

	public GitProject(Git tool, File dir) {
		super(tool, dir);
	}

	@Override
	public boolean isDirty() {
		String output = exec("status");
		return !output.contains("nothing to commit (working directory clean)");
	}

	@Override
	public String getLogAndDiffSince(String version) {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public String getVersion() {
		throw new RuntimeException("Not implemented yet.");
	}

	public boolean pull(String remote) {
		String output = remote == null ? exec("pull") : exec("pull", remote);
		output = output.trim();
		return !output.equals("Already up-to-date.");
	}

	@Override
	public boolean pullFromOrigin() {
		return pull(null);
	}

	private synchronized String exec(String... parameters) {
		return getTool().exec(getDir(), parameters);
	}

	@Override
	public Git getTool() {
		return (Git) super.getTool();
	}

}
