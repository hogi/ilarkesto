package ilarkesto.core.event;

import ilarkesto.core.logging.Log;

import java.util.Collection;

public abstract class AEventBus implements EventBus {

	public static final String DEFAULT_COMPONENT_NAME = "eventBus";

	private static Log log = Log.get(AEventBus.class);

	protected abstract Collection getPotentialEventHandlers();

	@Override
	public void fireEvent(Event event) {
		if (!(event instanceof Quiet)) log.debug("Firing event:", event);
		for (Object handler : getPotentialEventHandlers()) {
			event.tryToGetHandled(handler);
		}
	}

}
