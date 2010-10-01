package ilarkesto.scm.svn;

import ilarkesto.scm.AScmProject;
import ilarkesto.scm.AScmTool;
import ilarkesto.xml.JDom;

import java.io.File;

public class SvnProject extends AScmProject {

	SvnProject(AScmTool workspace, File dir) {
		super(workspace, dir);
	}

	@Override
	public boolean isDirty() {
		String output = exec("status");
		output = output.trim();
		return output.length() > 0;
	}

	@Override
	public String getLogAndDiffSince(String version) {
		if (version == null) version = "PREV";
		StringBuilder sb = new StringBuilder();
		sb.append("# Log since ").append(version).append(":\n\n");
		sb.append(getLogSince(version));
		sb.append("\n# Diff since ").append(version).append(":\n\n");
		sb.append(getDiffSince(version));
		return sb.toString();
	}

	public String getLogSince(String version) {
		if (version == null) throw new IllegalArgumentException("version == null");
		return exec("log", "--non-interactive", "-r", version + ":BASE");
	}

	public String getDiffSince(String version) {
		if (version == null) throw new IllegalArgumentException("version == null");
		return exec("diff", "--non-interactive", "-r", version);
	}

	@Override
	public boolean pullFromOrigin() {
		String output = exec("update", "--non-interactive");
		output = output.trim();
		return !output.startsWith("At revision ");
	}

	@Override
	public String getVersion() {
		String xml = exec("info", "--non-interactive", "--xml");
		return JDom.getChildAttributeValue(JDom.createDocument(xml), "entry", "revision");
	}

	private synchronized String exec(String... parameters) {
		return getTool().exec(getDir(), parameters);
	}

	@Override
	public Svn getTool() {
		return (Svn) super.getTool();
	}

}
