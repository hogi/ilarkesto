package ilarkesto.core.scope;

public interface ComponentReflector<C> {

	void injectComponents(C component, Scope scope);

	void callInitializationMethods(C component);

	void outjectComponents(C component, Scope scope);

}
