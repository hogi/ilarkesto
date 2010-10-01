package ilarkesto.mda.generator;

import ilarkesto.base.Str;
import ilarkesto.core.scope.ComponentReflector;
import ilarkesto.core.scope.Scope;
import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GwtComponentsReflectorGenerator extends AJavaClassGenerator implements NodeTypes {

	private Node gwtModule;
	private List<Node> components = new ArrayList<Node>();

	public GwtComponentsReflectorGenerator(String srcPath, Node gwtModule) {
		super(srcPath, true);
		this.gwtModule = gwtModule;
	}

	@Override
	protected void printCode(JavaPrinter out) {
		out.package_(getBasePackageName());
		out.beginClass(getClassName(), null, Arrays.asList(ComponentReflector.class.getName()));

		for (Node component : components)
			printField(out, component);

		out.beginProcedure("injectComponents", Arrays.asList("Object component", Scope.class.getName() + " scope"));
		for (Node component : components)
			out.statement("if (component instanceof " + getType(component) + ") " + getName(component)
					+ "Reflector.injectComponents(component, scope)");
		out.endProcedure();

		out.beginProcedure("callInitializationMethods", Arrays.asList("Object component"));
		for (Node component : components)
			out.statement("if (component instanceof " + getType(component) + ") " + getName(component)
					+ "Reflector.callInitializationMethods(component)");
		out.endProcedure();

		out.beginProcedure("outjectComponents", Arrays.asList("Object component", Scope.class.getName() + " scope"));
		for (Node component : components)
			out.statement("if (component instanceof " + getType(component) + ") " + getName(component)
					+ "Reflector.outjectComponents(component, scope)");
		out.endProcedure();

		for (Node component : components)
			printCreateMethod(out, component);

		out.endClass();
	}

	private void printCreateMethod(JavaPrinter out, Node component) {
		out.beginMethod(ComponentReflector.class.getName(), "create" + component.getValue() + "Reflector", null);
		out.returnStatement("new " + getReflectorType(component) + "()");
		out.endMethod();
	}

	private void printField(JavaPrinter out, Node component) {
		out.protectedField(ComponentReflector.class.getName(), getName(component) + "Reflector", "create"
				+ component.getValue() + "Reflector()");
	}

	private String getName(Node component) {
		return Str.lowercaseFirstLetter(component.getValue());
	}

	private String getType(Node component) {
		return getPackageName(component) + "." + component.getValue();
	}

	private String getReflectorType(Node component) {
		return getPackageName(component) + ".G" + component.getValue() + "Reflector";
	}

	private String getPackageName(Node component) {
		Node package_ = component.getSuperparentByType(Package);
		return getBasePackageName() + "." + package_.getValue();
	}

	private String getClassName() {
		return "G" + gwtModule.getValue() + "ComponentsReflector";
	}

	private String getBasePackageName() {
		return gwtModule.getValue().toLowerCase() + ".client";
	}

	public void addComponent(Node component) {
		components.add(component);
	}

}
