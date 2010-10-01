package ilarkesto.mda.legacy.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class EventModel extends AModel {

	private Set<ParameterModel> parameters = new LinkedHashSet<ParameterModel>();
	private boolean quiet;

	public EventModel(String name) {
		super(name);
		if (!Character.isUpperCase(name.charAt(0)))
			throw new RuntimeException("First letter of event name needs to be uppercase: " + name);
	}

	public EventModel addParameter(String name, String type) {
		ParameterModel parameter = new ParameterModel(name, type);
		parameters.add(parameter);
		return this;
	}

	public EventModel addParameter(String name, Class type) {
		return addParameter(name, type.getName());
	}

	public Set<ParameterModel> getParameters() {
		return parameters;
	}

	public boolean isQuiet() {
		return quiet;
	}

	public EventModel setQuiet(boolean quiet) {
		this.quiet = quiet;
		return this;
	}

}
