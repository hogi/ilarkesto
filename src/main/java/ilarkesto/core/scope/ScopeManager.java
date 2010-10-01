package ilarkesto.core.scope;

public abstract class ScopeManager {

	private static ScopeManager singletonInstance;

	private Scope rootScope;

	public ScopeManager(Scope rootScope) {
		assert singletonInstance == null;
		singletonInstance = this;
		this.rootScope = rootScope;
	}

	/**
	 * Gets the active scope.
	 */
	public abstract Scope getScope();

	protected Scope getRootScope() {
		return rootScope;
	}

	/**
	 * Gets the singleton instance of the active ScopeManager.
	 */
	public static ScopeManager getInstance() {
		assert singletonInstance != null;
		return singletonInstance;
	}

	@Override
	public String toString() {
		return getClass().getName() + "(" + getScope() + ")";
	}

}
