package ilarkesto.mda.model;

import ilarkesto.core.event.AEvent;

public class ModelChangedEvent extends AEvent {

	@Override
	public void tryToGetHandled(Object handler) {
		log.info("Testing event handler:", handler);
		if (handler instanceof ModelChangedHandler) {
			log.info("Calling event handler:", handler);
			((ModelChangedHandler) handler).onModelChanged(this);
		}
	}
}
