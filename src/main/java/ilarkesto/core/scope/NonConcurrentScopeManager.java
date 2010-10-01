package ilarkesto.core.scope;

import ilarkesto.core.logging.Log;

public class NonConcurrentScopeManager extends ScopeManager {

	private static Log log = Log.get(NonConcurrentScopeManager.class);

	private Scope currentScope;

	protected NonConcurrentScopeManager(Scope rootScope) {
		super(rootScope);
		this.currentScope = getRootScope();
	}

	public static NonConcurrentScopeManager createCascadingScopeInstance(String rootScopeName,
			ComponentReflector componentReflector) {
		return new NonConcurrentScopeManager(new CascadingScope(null, rootScopeName, componentReflector));
	}

	@Override
	public Scope getScope() {
		assert currentScope != null;
		return currentScope;
	}

	public Scope setScope(Scope scope) {
		assert scope != null;
		this.currentScope = scope;
		log.info("Scope activated:", scope);
		return scope;
	}

}
