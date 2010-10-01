package ilarkesto.mda.generator;

import ilarkesto.base.Str;
import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

public class GwtTextBundleGenerator extends AJavaClassGenerator implements NodeTypes {

	private Node bundle;
	private Node gwtModule;

	public GwtTextBundleGenerator(String srcPath, Node bundle) {
		super(srcPath, true);
		this.bundle = bundle;

		gwtModule = bundle.getSuperparentByType(GwtModule);
		assert gwtModule != null;

	}

	@Override
	protected void printCode(JavaPrinter out) {
		out.package_(getBasePackageName() + ".i18n");
		out.beginClass(getClassName(), null, null);

		out.loggerByClassName();

		for (Node text : bundle.getChildrenByType(Text)) {
			out.beginMethod("String", text.getValue(), null);
			String en = text.getChildValueByType(EN);
			if (Str.isBlank(en)) {
				out.returnStatement("null");
			} else {
				out.returnStatement("\"" + Str.escapeEscapeSequences(en) + "\"");
			}
			out.endMethod();
		}

		out.endClass();
	}

	private String getClassName() {
		return "TextBundle" + bundle.getValue();
	}

	private String getBasePackageName() {
		return gwtModule.getValue().toLowerCase() + ".client";
	}
}
