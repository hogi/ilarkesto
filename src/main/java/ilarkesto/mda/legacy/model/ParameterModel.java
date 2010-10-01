package ilarkesto.mda.legacy.model;

public class ParameterModel extends AModel {

	private String type;

	public ParameterModel(String name, String type) {
		super(name);
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
