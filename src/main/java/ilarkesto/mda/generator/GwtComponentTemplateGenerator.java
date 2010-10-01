package ilarkesto.mda.generator;

import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

public class GwtComponentTemplateGenerator extends AJavaClassGenerator implements NodeTypes {

	private Node component;
	private Node package_;
	private Node gwtModule;

	public GwtComponentTemplateGenerator(String srcPath, Node component) {
		super(srcPath, false);
		this.component = component;

		package_ = component.getSuperparentByType(Package);
		assert package_ != null;
		gwtModule = package_.getSuperparentByType(GwtModule);
		assert gwtModule != null;

	}

	@Override
	protected void printCode(JavaPrinter out) {
		out.package_(getPackageName());
		out.beginClass(getClassName(), getSuperclassName(), null);
		out.endClass();
	}

	private String getSuperclassName() {
		return "G" + component.getValue();
	}

	private String getClassName() {
		return component.getValue();
	}

	private String getPackageName() {

		String packageName = getBasePackageName() + "." + package_.getValue();

		return packageName;
	}

	private String getBasePackageName() {
		return gwtModule.getValue().toLowerCase() + ".client";
	}
}
