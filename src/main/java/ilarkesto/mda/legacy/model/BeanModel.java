package ilarkesto.mda.legacy.model;

import ilarkesto.base.Str;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class BeanModel extends AModel {

	private String packageName;
	private Set<DependencyModel> dependencies = new LinkedHashSet<DependencyModel>();
	private Set<CompositeModel> composites = new LinkedHashSet<CompositeModel>();
	private Set<EventModel> events = new LinkedHashSet<EventModel>();
	private List<PredicateModel> predicates = new ArrayList<PredicateModel>();
	private boolean _abstract;

	private BeanModel superbean;
	private String superclass;

	public BeanModel(String name, String packageName) {
		super(name);
		this.packageName = packageName;
	}

	public PredicateModel addPredicate(String name) {
		PredicateModel predicate = new PredicateModel(name);
		predicates.add(predicate);
		return predicate;
	}

	public List<PredicateModel> getPredicates() {
		return predicates;
	}

	@Deprecated
	public boolean isEntity() {
		return false;
	}

	@Deprecated
	public boolean isValueObject() {
		return false;
	}

	public String getBeanClass() {
		return getPackageName() + "." + getName();
	}

	public Set<CompositeModel> getComposites() {
		return composites;
	}

	public void addDaosAsComposites(Collection<EntityModel> entites) {
		for (EntityModel entity : entites) {
			addDaoAsComposite(entity);
		}
	}

	public CompositeModel addDaoAsComposite(EntityModel entity) {
		return addComposite(entity.getPackageName() + "." + entity.getDaoName(), entity.getDaoName());
	}

	public CompositeModel addComposite(BeanModel bean) {
		return addComposite(bean.getBeanClass(), bean.getName());
	}

	public CompositeModel addComposite(String type, String name) {
		CompositeModel composite = new CompositeModel(type, name);
		composites.add(composite);
		return composite;
	}

	public CompositeModel addComposite(String type) {
		if (!Character.isUpperCase(type.charAt(0)))
			throw new RuntimeException("Type needs to start with uppercase character: " + type);
		return addComposite(type, Str.lowercaseFirstLetter(type));
	}

	public EventModel addEvent(String name) {
		EventModel event = new EventModel(name);
		events.add(event);
		return event;
	}

	public Set<EventModel> getEvents() {
		return events;
	}

	public String getAbstractBaseClassName() {
		return "G" + getName();
	}

	public final String getPackageName() {
		return packageName;
	}

	public final String getGwtPackageName() {
		return packageName.replace(".server", ".client");
	}

	public Set<DependencyModel> getDependencies() {
		return dependencies;
	}

	private void addDependency(DependencyModel dependencyModel) {
		dependencies.add(dependencyModel);
	}

	public boolean containsDependency(String name) {
		for (DependencyModel dm : dependencies) {
			if (dm.getName().equals(name)) return true;
		}
		return false;
	}

	protected DependencyModel addDependency(String type, String name) {
		// DEBUG.out("addDependency",type,name);
		DependencyModel dependencyModel = new DependencyModel(type, name);
		addDependency(dependencyModel);
		return dependencyModel;
	}

	public boolean isAbstract() {
		return _abstract;
	}

	public void setAbstract(boolean value) {
		this._abstract = value;
	}

	public String getSuperclass() {
		if (superbean != null) { return superbean.getName(); }
		return superclass;
	}

	public BeanModel getSuperbean() {
		return superbean;
	}

	public void setSuperbean(BeanModel superentity) {
		if (superclass != null) throw new RuntimeException("superclass already set");
		this.superbean = superentity;
	}

	public void setSuperclass(String superClass) {
		if (superbean != null) throw new RuntimeException("superbean already set");
		this.superclass = superClass;
	}

}
