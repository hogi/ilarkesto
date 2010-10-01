package ilarkesto.di.app;

import ilarkesto.core.logging.Log;
import ilarkesto.di.BeanContainer;
import ilarkesto.webapp.AWebApplication;

public class WebApplicationStarter extends ApplicationStarter {

	private static final Log LOG = Log.get(WebApplicationStarter.class);

	public static AWebApplication startWebApplication(String applicationClassName, String applicationName) {
		AWebApplication result;
		BeanContainer beanProvider = new BeanContainer();
		if (applicationName != null) {
			beanProvider.put("applicationName", applicationName);
		}
		try {
			result = startApplication((Class<? extends AWebApplication>) Class.forName(applicationClassName),
				beanProvider);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		LOG.debug("Triggering Garbage Collection");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}

		return result;
	}

}
