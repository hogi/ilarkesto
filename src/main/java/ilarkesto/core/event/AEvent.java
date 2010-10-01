package ilarkesto.core.event;

import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.core.scope.Scope;

public abstract class AEvent implements Event {

	protected Log log = Log.get(this.toString());

	public final void fireInCurrentScope() {
		AEventBus eventBus = (AEventBus) Scope.get().getComponent(AEventBus.DEFAULT_COMPONENT_NAME);
		if (eventBus == null)
			throw new IllegalStateException("Missing component in scope: " + AEventBus.DEFAULT_COMPONENT_NAME);
		eventBus.fireEvent(this);
	}

	@Override
	public String toString() {
		return Str.getSimpleName(getClass());
	}

}
