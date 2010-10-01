package ilarkesto.gwt.client;

import ilarkesto.core.logging.Log;

public class AComponent {

	protected final Log log = Log.get(getClass());

	private boolean initialized;
	private boolean destroyed;

	public AComponent() {}

	protected void onInitialization() {}

	protected void onDestroy() {}

	public final void initialize() {
		assert !initialized;
		onInitialization();
		initialized = true;
	}

	public final void destroy() {
		assert !destroyed;
		assert initialized;
		onDestroy();
		destroyed = true;
	}

	@Override
	public String toString() {
		return Gwt.getSimpleName(getClass());
	}

}
