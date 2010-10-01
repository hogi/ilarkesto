package ilarkesto.core.scope;

import ilarkesto.core.event.AEventBus;

import java.util.Collection;

public class CascadingScopeLocalEventBus extends AEventBus {

	private CascadingScope scope;

	public CascadingScopeLocalEventBus(CascadingScope scope) {
		super();
		this.scope = scope;
	}

	@Override
	protected Collection getPotentialEventHandlers() {
		return scope.getLocalComponents();
	}

}
