package ilarkesto.mda.legacy.generator;

import ilarkesto.gwt.client.AGwtApplication;
import ilarkesto.mda.legacy.model.ApplicationModel;

public class GwtApplicationGenerator extends AClassGenerator {

	private ApplicationModel application;

	public GwtApplicationGenerator(ApplicationModel application) {
		super();
		this.application = application;
	}

	@Override
	protected String getName() {
		return "G" + application.getName() + "GwtApplication";
	}

	@Override
	protected boolean isInterface() {
		return false;
	}

	@Override
	protected boolean isOverwrite() {
		return true;
	}

	@Override
	protected String getSuperclass() {
		return AGwtApplication.class.getName();
	}

	@Override
	protected String getPackage() {
		return application.getPackageName().replace(".server", ".client");
	}

	@Override
	protected void writeContent() {}
}
