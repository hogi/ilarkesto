package ilarkesto.mda.legacy.model;

public class CompositeModel extends AModel {

	private String type;

	public CompositeModel(String type, String name) {
		super(name);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return getName() + ":" + getType();
	}

}
