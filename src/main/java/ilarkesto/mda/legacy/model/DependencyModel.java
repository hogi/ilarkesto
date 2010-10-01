package ilarkesto.mda.legacy.model;

public class DependencyModel extends AModel {

	private String type;

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return getName().equals(((DependencyModel) obj).getName());
	}

	// --- dependencies ---

	public DependencyModel(String type, String name) {
		super(name);
		this.type = type;
	}

}
