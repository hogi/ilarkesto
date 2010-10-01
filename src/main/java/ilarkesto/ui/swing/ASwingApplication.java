package ilarkesto.ui.swing;

import ilarkesto.base.Str;
import ilarkesto.di.app.AApplication;
import ilarkesto.locale.LearningLocalizer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

public abstract class ASwingApplication extends AApplication {

	protected abstract void onStartSwing();

	protected void onShutdownSwing() {}

	@Override
	protected final void onStart() {
		onStartSwing();
	}

	@Override
	protected final void onShutdown() {
		onShutdownSwing();
		getTaskManager().shutdown(3000);
		System.exit(0);
	}

	// --- default beans ---

	private SwingUi ui;

	public SwingUi getUi() {
		if (ui == null) {
			ui = new SwingUi();
			autowire(ui);
		}
		return ui;
	}

	private LearningLocalizer localizer;

	public LearningLocalizer getLocalizer() {
		if (localizer == null) {
			localizer = new LearningLocalizer();
			autowire(localizer);
			localizer.setLocale(Locale.GERMANY);
		}
		return localizer;
	}

	private WindowAdapter shutdownWindowListener;

	public WindowAdapter getShutdownWindowListener() {
		if (shutdownWindowListener == null) {
			shutdownWindowListener = new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					shutdown();
				}

			};
			autowire(shutdownWindowListener);
		}
		return shutdownWindowListener;
	}

	// --- ---

	@Override
	public String getApplicationName() {
		return Str.removeSuffix(super.getApplicationName(), "Swing");
	}

}
