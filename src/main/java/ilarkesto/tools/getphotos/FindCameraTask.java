package ilarkesto.tools.getphotos;

import ilarkesto.base.Env;
import ilarkesto.concurrent.ATask;
import ilarkesto.core.logging.Log;

import java.io.File;

public class FindCameraTask extends ATask {

	private static final Log LOG = Log.get(FindCameraTask.class);

	@Override
	protected void perform() throws InterruptedException {
		while (!isAbortRequested()) {
			for (File root : Env.get().getMountedDirs()) {
				File dcimDir = getDcimDir(root);
				if (dcimDir != null) {
					LOG.info("DCIM dir found:", dcimDir);
					GetphotosSwingApplication.get().startCopying(dcimDir);
					return;
				}
				if (isAbortRequested()) break;
			}
			sleep(1000);
		}
	}

	private File getDcimDir(File dir) {
		LOG.debug("Checking", dir);
		File dcimDir = new File(dir.getPath() + "/DCIM");
		return dcimDir.exists() ? dcimDir : null;
	}

}
