package ilarkesto.mda.legacy.model;

import java.util.ArrayList;
import java.util.List;

public class ActionModel extends AModel {

	private List<ParameterModel> parameters = new ArrayList<ParameterModel>();
	private String packageName;

	public ActionModel(String name, String packageName) {
		super(name);
		this.packageName = packageName;
	}

	public ActionModel addParameter(String name, String type) {
		ParameterModel parameter = new ParameterModel(name, type);
		parameters.add(parameter);
		return this;
	}

	public List<ParameterModel> getParameters() {
		return parameters;
	}

	public String getPackageName() {
		return packageName;
	}

	// --- helper ---

	public ActionModel addParameter(String name, Class type) {
		return addParameter(name, type.getName());
	}

	public ActionModel addParameter(String name, BeanModel bean) {
		return addParameter(name, bean.getBeanClass());
	}
}
