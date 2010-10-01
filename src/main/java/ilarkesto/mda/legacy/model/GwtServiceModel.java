package ilarkesto.mda.legacy.model;

import java.util.ArrayList;
import java.util.List;

public class GwtServiceModel extends AModel {

	private String packageName;
	private List<MethodModel> methods = new ArrayList<MethodModel>();

	public GwtServiceModel(String name, String packageName) {
		super(name);
		this.packageName = packageName;
	}

	public MethodModel addMethod(String name) {
		MethodModel methodModel = new MethodModel(name);
		methods.add(methodModel);
		return methodModel;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<MethodModel> getMethods() {
		return methods;
	}

}
