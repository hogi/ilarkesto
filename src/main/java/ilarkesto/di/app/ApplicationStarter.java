package ilarkesto.di.app;

import ilarkesto.base.Sys;
import ilarkesto.base.time.DateAndTime;
import ilarkesto.cli.ACommand;
import ilarkesto.core.logging.Log;
import ilarkesto.di.BeanContainer;
import ilarkesto.di.BeanProvider;
import ilarkesto.di.MultiBeanProvider;
import ilarkesto.io.IO;
import ilarkesto.logging.DefaultLogDataHandler;
import ilarkesto.logging.JavaLogging;
import ilarkesto.logging.Log4jLogging;

import java.util.Locale;

public class ApplicationStarter {

	private static final Log LOG = Log.get(ApplicationStarter.class);

	public static <A extends AApplication> A startApplication(Class<A> applicationClass, BeanProvider beanProvider,
			String... arguments) {

		Sys.storeStartupTime();
		DefaultLogDataHandler.activate();
		Log.setDebugEnabled(Sys.isDevelopmentMode());
		LOG.info("********************************************************************************");
		LOG.info("Starting application:", applicationClass.getName());
		logEnvironmentInfo();
		LOG.info("    arguments:   ", arguments);

		try {
			A application = applicationClass.newInstance();
			if (beanProvider != null) beanProvider.autowire(application);
			DefaultLogDataHandler.setLogFileToHomeOrWorkdir(application.getApplicationName());
			JavaLogging.redirectToLoggers();
			try {
				Log4jLogging.redirectToLoggers();
			} catch (Throwable ex) {}
			application.setArguments(arguments);
			application.start();
			LOG.info("Application started:", application);
			LOG.info("********************************************************************************\n");
			return application;
		} catch (Throwable ex) {
			LOG.fatal("Starting application failed.", ex);
			throw new RuntimeException(ex);
		}
	}

	public static void logEnvironmentInfo() {
		String mode = Sys.isDevelopmentMode() ? "DEVELOPMENT" : "PRODUCTION";
		LOG.info("   ", mode, "MODE");
		LOG.info("    time:        ", new DateAndTime(Sys.getStartupTime()));
		LOG.info("    system user: ", Sys.getUsersName());
		LOG.info("    user home:   ", Sys.getUsersHomePath());
		LOG.info("    work-path:   ", IO.getWorkDir());
		LOG.info("    temp-path:   ", IO.getTempDir());
		LOG.info("    locale:      ", Locale.getDefault());
		LOG.info("    encoding:    ", Sys.getFileEncoding());
		LOG.info("    java:        ", Sys.getJavaHome());
		// LOG.info(" java version: ", Sys.getJavaRuntimeVersion());
	}

	public static <A extends AApplication> A startApplication(Class<A> applicationClass, String... arguments) {
		return startApplication(applicationClass, new MultiBeanProvider(), arguments);
	}

	public static void executeCommand(Class<? extends ACommand> commandClass, String... arguments) {
		BeanContainer beanContainer = new BeanContainer();
		beanContainer.put("commandClass", commandClass);
		startApplication(CommandApplication.class, beanContainer, arguments);
	}

	// --- dependencies ---

}
