package ilarkesto.mda.generator;

import ilarkesto.base.Str;
import ilarkesto.mda.model.Node;
import ilarkesto.mda.model.NodeTypes;

import java.util.Arrays;
import java.util.List;

public class GwtServiceInterfaceGenerator extends AJavaClassGenerator implements NodeTypes {

	private Node module;

	public GwtServiceInterfaceGenerator(String srcPath, Node module) {
		super(srcPath, true);
		this.module = module;
	}

	@Override
	protected void printCode(JavaPrinter out) {
		out.package_(getPackageName());
		out.beginInterface(module.getValue() + "Service", Arrays
				.asList(com.google.gwt.user.client.rpc.RemoteService.class.getName()));

		List<Node> calls = module.getChildrenByTypeRecursive(ServiceCall);
		for (Node call : calls) {
			List<String> params = getParameterTypesAndNames(call, "String");
			params.add(0, "int conversationNumber");
			out.interfaceMethod(getPackageName() + ".DataTransferObject", Str.lowercaseFirstLetter(call.getValue()),
				params);
		}

		out.endInterface();
	}

	private String getPackageName() {
		return module.getValue().toLowerCase() + ".client";
	}
}
