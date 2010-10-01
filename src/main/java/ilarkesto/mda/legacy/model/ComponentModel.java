package ilarkesto.mda.legacy.model;

public class ComponentModel extends BeanModel {

	private boolean gwt;

	public ComponentModel(String name, String packageName) {
		super(name, packageName);
	}

	public boolean isGwt() {
		return gwt;
	}

	public void setGwt(boolean gwt) {
		this.gwt = gwt;
	}

}
