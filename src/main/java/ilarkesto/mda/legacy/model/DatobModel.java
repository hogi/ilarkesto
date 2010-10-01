package ilarkesto.mda.legacy.model;

import ilarkesto.base.Str;
import ilarkesto.persistence.AEntity;

import java.util.LinkedHashSet;
import java.util.Set;

public class DatobModel extends BeanModel {

	private Set<PropertyModel> properties = new LinkedHashSet<PropertyModel>();
	private boolean searchable;
	private boolean gwtSupport;

	public DatobModel(String name, String packageName) {
		super(name, packageName);
	}

	@Override
	public boolean isValueObject() {
		return true;
	}

	public boolean isGwtSupport() {
		return gwtSupport;
	}

	public void setGwtSupport(boolean gwtSupport) {
		this.gwtSupport = gwtSupport;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public final boolean isSearchable() {
		if (searchable) return true;
		for (PropertyModel p : getProperties()) {
			if (p.isSearchable()) return true;
		}
		return false;
	}

	public final Set<PropertyModel> getProperties() {
		return properties;
	}

	public StringPropertyModel addStringProperty(String name) {
		StringPropertyModel propertyModel = new StringPropertyModel(this, name);
		properties.add(propertyModel);
		return propertyModel;
	}

	public IntegerPropertyModel addIntegerProperty(String name) {
		IntegerPropertyModel propertyModel = new IntegerPropertyModel(this, name);
		properties.add(propertyModel);
		return propertyModel;
	}

	public SimplePropertyModel addProperty(String name, Class type) {
		SimplePropertyModel propertyModel = new SimplePropertyModel(this, name, false, false, type.getName());
		properties.add(propertyModel);
		return propertyModel;
	}

	public ListPropertyModel addListProperty(String name, Class type) {
		ListPropertyModel propertyModel = new ListPropertyModel(this, name, false, type);
		properties.add(propertyModel);
		return propertyModel;
	}

	public SetPropertyModel addSetProperty(String name, Class type) {
		SetPropertyModel propertyModel = new SetPropertyModel(this, name, false, type);
		properties.add(propertyModel);
		return propertyModel;
	}

	public SetPropertyModel addSetProperty(String name, BeanModel type) {
		boolean valueObject = type.isValueObject();
		SetPropertyModel propertyModel = new SetPropertyModel(this, name, false, valueObject, type.getPackageName()
				+ "." + type.getName());
		propertyModel.setSearchable(true);
		properties.add(propertyModel);
		return propertyModel;
	}

	public SimplePropertyModel addReference(String name, EntityModel type) {
		String className = type.getPackageName() + "." + type.getName();
		SimplePropertyModel propertyModel = new SimplePropertyModel(this, name, type);
		propertyModel.setAbstract(type.isAbstract());
		properties.add(propertyModel);
		if (!"User".equals(type.getName()) && !AEntity.class.getName().equals(className) && !type.isAbstract()
				&& !type.equals(this))
			addDependency(type.getPackageName() + "." + type.getName() + "Dao", Str.lowercaseFirstLetter((type
					.getName()))
					+ "Dao");
		return propertyModel;
	}

	public SetPropertyModel addSetReference(String name, BeanModel type) {
		String className = type.getPackageName() + "." + type.getName();
		SetPropertyModel propertyModel = new SetPropertyModel(this, name, true, false, className);
		propertyModel.setAbstract(type.isAbstract());
		properties.add(propertyModel);
		if (!"User".equals(type.getName()) && !AEntity.class.getName().equals(className))
			addDependency(type.getPackageName() + "." + type.getName() + "Dao", Str.lowercaseFirstLetter((type
					.getName()))
					+ "Dao");
		return propertyModel;
	}

	public ListPropertyModel addListReference(String name, BeanModel type) {
		String className = type.getPackageName() + "." + type.getName();
		ListPropertyModel propertyModel = new ListPropertyModel(this, name, true, false, className);
		propertyModel.setAbstract(type.isAbstract());
		properties.add(propertyModel);
		if (!"User".equals(type.getName()) && !AEntity.class.getName().equals(className))
			addDependency(type.getPackageName() + "." + type.getName() + "Dao", Str.lowercaseFirstLetter((type
					.getName()))
					+ "Dao");
		return propertyModel;
	}
}
