package ilarkesto.di.app;

import ilarkesto.concurrent.TaskManager;
import ilarkesto.core.logging.Log;

public abstract class ACommandLineApplication extends AApplication {

	private static final Log LOG = Log.get(ACommandLineApplication.class);

	protected abstract int execute(String[] args);

	@Override
	protected final void onStart() {
		int rc;
		try {
			rc = execute(getArguments());
		} catch (Throwable ex) {
			LOG.fatal(ex);
			rc = 1;
		}
		Log.flush();
		System.exit(rc);
	}

	@Override
	public void ensureIntegrity() {}

	@Override
	protected final void onShutdown() {}

	@Override
	protected final void scheduleTasks(TaskManager tm) {}

}
