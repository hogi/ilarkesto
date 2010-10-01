package ilarkesto.core.scope;

import ilarkesto.core.event.AEventBus;

import java.util.Collection;

public class CascadingScopeEventBus extends AEventBus {

	private CascadingScope scope;

	public CascadingScopeEventBus(CascadingScope scope) {
		super();
		this.scope = scope;
	}

	@Override
	protected Collection getPotentialEventHandlers() {
		return scope.getAllComponents();
	}

}
