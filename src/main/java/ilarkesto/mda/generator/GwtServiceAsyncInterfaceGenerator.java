package ilarkesto.mda.generator;

import ilarkesto.base.Str;
import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class GwtServiceAsyncInterfaceGenerator extends AJavaClassGenerator implements NodeTypes {

	private Node module;

	public GwtServiceAsyncInterfaceGenerator(String srcPath, Node module) {
		super(srcPath, true);
		this.module = module;
	}

	@Override
	protected void printCode(JavaPrinter out) {
		out.package_(getPackageName());
		out.beginInterface(module.getValue() + "ServiceAsync", null);

		List<Node> calls = module.getChildrenByTypeRecursive(ServiceCall);
		for (Node call : calls) {
			List<String> params = getParameterTypesAndNames(call, "String");
			params.add(0, "int conversationNumber");
			params.add(AsyncCallback.class.getName() + "<" + getPackageName() + ".DataTransferObject> callback");
			out.interfaceMethod("void", Str.lowercaseFirstLetter(call.getValue()), params);
		}

		out.endInterface();
	}

	private String getPackageName() {
		return module.getValue().toLowerCase() + ".client";
	}
}
