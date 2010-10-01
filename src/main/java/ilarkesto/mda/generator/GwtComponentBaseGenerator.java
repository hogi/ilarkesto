package ilarkesto.mda.generator;

import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

public class GwtComponentBaseGenerator extends AJavaClassGenerator implements NodeTypes {

	private Node component;
	private Node package_;
	private Node gwtModule;

	public GwtComponentBaseGenerator(String srcPath, Node component) {
		super(srcPath, true);
		this.component = component;

		package_ = component.getSuperparentByType(Package);
		assert package_ != null;
		gwtModule = package_.getSuperparentByType(GwtModule);
		assert gwtModule != null;

	}

	@Override
	protected void printCode(JavaPrinter out) {
		out.package_(getPackageName());
		out.beginClass(true, getClassName(), null, null);

		out.loggerByClassName(component.getValue());

		for (Node initProc : component.getChildrenByType(InitializationProcedure)) {
			printInitializationProcedure(out, initProc);
		}

		for (Node dependency : component.getChildrenByType(Dependency)) {
			printDependency(out, dependency);
		}

		out.toStringMethod('"' + component.getValue() + '"');

		out.endClass();
	}

	private void printInitializationProcedure(JavaPrinter out, Node initProc) {
		out.abstractMethod("void", initProc.getValue(), null);
	}

	private void printDependency(JavaPrinter out, Node dependency) {
		out.protectedField(getDependencyType(dependency), dependency.getValue());
	}

	private String getClassName() {
		return "G" + component.getValue();
	}

	private String getPackageName() {

		String packageName = getBasePackageName() + "." + package_.getValue();

		return packageName;
	}

	private String getBasePackageName() {
		return gwtModule.getValue().toLowerCase() + ".client";
	}
}
