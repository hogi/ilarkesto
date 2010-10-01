package ilarkesto.core.scope;

import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;

import java.util.List;

public abstract class Scope {

	static Log log = Log.get(Scope.class);

	/**
	 * Gets an initialized component by name.
	 */
	public abstract Object getComponent(String name);

	public abstract List getAllComponents();

	public abstract <T> T putComponent(String name, T component);

	public String getName() {
		return Str.getSimpleName(getClass());
	}

	@Override
	public String toString() {
		return getName();
	}

	// --- helper ---

	public <T> T putComponent(T component) {
		if (component == null) throw new IllegalArgumentException("component == null");
		return putComponent(ScopeUtil.getComponentName(component.getClass()), component);
	}

	public <T> T getComponent(Class<T> type) {
		String name = ScopeUtil.getComponentName(type);
		return (T) getComponent(name);
	}

	public static Scope get() {
		return ScopeManager.getInstance().getScope();
	}

}