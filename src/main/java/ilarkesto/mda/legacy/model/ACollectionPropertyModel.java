package ilarkesto.mda.legacy.model;

public abstract class ACollectionPropertyModel extends PropertyModel {

	protected abstract Class getCollectionTypeClass();

	protected abstract Class getCollectionImplClass();

	@Override
	public final String getType() {
		return getCollectionType() + "<" + getContentType() + ">";
	}

	@Override
	public String getCollectionType() {
		return getCollectionTypeClass().getName();
	}

	@Override
	public String getCollectionImpl() {
		return getCollectionImplClass().getName();
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public final boolean isCollection() {
		return true;
	}

	@Override
	public String getNameSingular() {
		String name = getName();
		if (!name.endsWith("s")) throw new RuntimeException("property name must end with 's', but does not: " + name);
		return name.substring(0, name.length() - 1);
	}

	@Override
	public boolean isBoolean() {
		return false;
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

	@Override
	public boolean isString() {
		return false;
	}

	// --- dependencies ---

	private String contentType;

	public ACollectionPropertyModel(BeanModel entityModel, String name, boolean reference, boolean valueObject,
			String contentType) {
		super(entityModel, name, reference, valueObject);
		this.contentType = contentType;
	}

	public ACollectionPropertyModel(BeanModel entityModel, String name, boolean reference, boolean valueObject,
			Class contentType) {
		this(entityModel, name, reference, valueObject, contentType.getName());
	}

}
