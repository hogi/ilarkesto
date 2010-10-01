package ilarkesto.mda.legacy.model;

import java.util.ArrayList;
import java.util.List;

public class MethodModel extends AModel {

	private List<ParameterModel> parameters = new ArrayList<ParameterModel>();

	public MethodModel(String name) {
		super(name);
	}

	public MethodModel addParameter(String name, String type) {
		ParameterModel parameter = new ParameterModel(name, type);
		parameters.add(parameter);
		return this;
	}

	public List<ParameterModel> getParameters() {
		return parameters;
	}

	// --- helper ---

	public MethodModel addParameter(String name, Class type) {
		return addParameter(name, type.getName());
	}
}
