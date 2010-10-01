package ilarkesto.mswin;

import ilarkesto.base.Proc;

/**
 * Utility for mounting and unmounting of drives on MsWindows.
 * 
 * @author wko
 */
public class DriveMounter {

	public static void main(String[] args) {
		mount('b', "\\\\devsrv1\\bpm", "DA100001\\a101zi8", "!23geheim", false);
		// unmount('b');
	}

	// TODO public static Map<Character,String> getMountedDrives();

	public static void mount(char driveLetter, String networkSharePath, String user, String password,
			boolean persistent) {
		Proc proc = new Proc("NET");
		proc.addParameter("USE");
		proc.addParameter(driveLetter + ":");
		proc.addParameter(networkSharePath);
		if (password != null) proc.addParameter(password);
		if (user != null) proc.addParameter("/USER:" + user);
		proc.addParameter("/PERSISTENT:" + (persistent ? "YES" : "NO"));

		proc.start();
		if (proc.getReturnCode() != 0) { throw new RuntimeException(proc.getOutput()); }
	}

	public static void unmount(char driveLetter) {
		Proc proc = new Proc("NET");
		proc.addParameter("USE");
		proc.addParameter(driveLetter + ":");
		proc.addParameter("/DELETE");

		proc.start();
		if (proc.getReturnCode() != 0) { throw new RuntimeException(proc.getOutput()); }
	}

	private DriveMounter() {}

}
