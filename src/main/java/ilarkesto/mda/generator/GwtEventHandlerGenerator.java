package ilarkesto.mda.generator;

import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

import java.util.Arrays;

public class GwtEventHandlerGenerator extends AJavaClassGenerator implements NodeTypes {

	private Node event;
	private Node package_;
	private Node gwtModule;

	public GwtEventHandlerGenerator(String srcPath, Node event) {
		super(srcPath, true);
		this.event = event;

		package_ = event.getSuperparentByType(Package);
		assert package_ != null;
		gwtModule = package_.getSuperparentByType(GwtModule);
		assert gwtModule != null;

	}

	@Override
	protected void printCode(JavaPrinter out) {
		out.package_(getPackageName());
		out.beginInterface(event.getValue() + "Handler", null);

		out.abstractMethod(null, "on" + event.getValue(), Arrays.asList(event.getValue() + "Event event"));

		out.endInterface();
	}

	private String getPackageName() {
		String packageName = getBasePackageName() + "." + package_.getValue();
		return packageName;
	}

	private String getBasePackageName() {
		return gwtModule.getValue().toLowerCase() + ".client";
	}
}
