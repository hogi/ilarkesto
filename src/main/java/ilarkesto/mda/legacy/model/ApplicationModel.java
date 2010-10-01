package ilarkesto.mda.legacy.model;

import java.util.ArrayList;
import java.util.List;

public class ApplicationModel extends BeanModel {

	public enum Type {
		WEB, SWING
	}

	private Type type;
	private List<GwtServiceModel> gwtServices = new ArrayList<GwtServiceModel>();
	private List<ActionModel> actions = new ArrayList<ActionModel>();

	public ApplicationModel(Type type, String name, String packageName) {
		super(name, packageName);
		this.type = type;
	}

	public ActionModel addAction(String name, String packageName) {
		ActionModel action = new ActionModel(name, packageName);
		actions.add(action);
		return action;
	}

	public ActionModel addCreateAction(EntityModel entity) {
		return addAction("Create" + entity.getName(), entity.getPackageName());
	}

	public List<ActionModel> getActions() {
		return actions;
	}

	public Type getType() {
		return type;
	}

	public void addGwtService(GwtServiceModel serviceModel) {
		gwtServices.add(serviceModel);
	}

	public List<GwtServiceModel> getGwtServices() {
		return gwtServices;
	}

}
