package ilarkesto.mda.legacy.model;

import java.util.ArrayList;
import java.util.List;

public class PredicateModel extends AModel {

	private List<ParameterModel> parameters = new ArrayList<ParameterModel>();

	public PredicateModel(String name) {
		super(name);
	}

	public PredicateModel addParameter(String name, String type) {
		ParameterModel parameter = new ParameterModel(name, type);
		parameters.add(parameter);
		return this;
	}

	public List<ParameterModel> getParameters() {
		return parameters;
	}

	// --- helper ---

	public PredicateModel addParameter(String name, Class type) {
		return addParameter(name, type.getName());
	}

}
