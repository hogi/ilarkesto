package ilarkesto.mda.legacy.generator;

import ilarkesto.mda.legacy.model.ApplicationModel;
import ilarkesto.ui.swing.ASwingApplication;
import ilarkesto.webapp.AWebApplication;

public class ApplicationGenerator extends ABeanGenerator<ApplicationModel> {

	public ApplicationGenerator(ApplicationModel bean) {
		super(bean);
	}

	@Override
	protected String getSuperclass() {
		switch (bean.getType()) {
			case SWING:
				return ASwingApplication.class.getName();
			case WEB:
				return AWebApplication.class.getName();
		}
		throw new RuntimeException("Unsupported application type: " + bean.getType());
	}

	@Override
	protected String getName() {
		String suffix = "";
		switch (bean.getType()) {
			case SWING:
				suffix = "SwingApplication";
				break;
			case WEB:
				suffix = "WebApplication";
				break;
		}
		return "G" + bean.getName() + suffix;
	}

}
