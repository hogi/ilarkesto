package ilarkesto.mda.swingeditor;

import ilarkesto.core.scope.CascadingScope;
import ilarkesto.core.scope.NonConcurrentScopeManager;
import ilarkesto.core.scope.Scope;
import ilarkesto.mda.model.ModellingSession;
import ilarkesto.scope.ReflectionComponentReflector;

public class Starter {

	public static void main(String[] args) {
		createModellerScope();

		Scope.get().getComponent(Workspace.class).showJFrame();
	}

	public static Scope createModellerScope() {
		NonConcurrentScopeManager.createCascadingScopeInstance("app", new ReflectionComponentReflector());

		CascadingScope scope = CascadingScope.get();
		scope.putComponent(new Workspace());
		scope.putComponent(new ModellingSession());
		scope.putComponent(new SwingModelHelper());
		scope.putComponent(new NodeListBarPanel());
		scope.putComponent(new NodeValuePanel());
		scope.putComponent(new SaveAction());
		scope.putComponent(new ProcessAction());

		return scope;
	}

}
