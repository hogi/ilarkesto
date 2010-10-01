package ilarkesto.mda.legacy.model;

public class BackReferenceModel extends AModel {

	private SimplePropertyModel property;

	public BackReferenceModel(String name, SimplePropertyModel property) {
		super(name);
		this.property = property;
	}

	public SimplePropertyModel getProperty() {
		return property;
	}

}
