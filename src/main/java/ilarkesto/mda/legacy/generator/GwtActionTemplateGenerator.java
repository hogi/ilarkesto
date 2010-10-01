package ilarkesto.mda.legacy.generator;

import ilarkesto.mda.legacy.model.ActionModel;
import ilarkesto.mda.legacy.model.ParameterModel;

public class GwtActionTemplateGenerator extends AClassGenerator {

	private ActionModel action;

	public GwtActionTemplateGenerator(ActionModel action) {
		super();
		this.action = action;
	}

	@Override
	protected final String getName() {
		return action.getName() + "Action";
	}

	@Override
	protected final String getPackage() {
		return action.getPackageName().replace(".server", ".client");
	}

	@Override
	protected final boolean isInterface() {
		return false;
	}

	@Override
	protected void writeContent() {
		if (!action.getParameters().isEmpty()) writeConstructor();
	}

	private void writeConstructor() {
		ln();
		StringBuilder sig = new StringBuilder();
		{
			boolean first = true;
			for (ParameterModel parameter : action.getParameters()) {
				if (first) {
					first = false;
				} else {
					sig.append(", ");
				}
				sig.append(parameter.getType().replace(".server", ".client")).append(" ").append(parameter.getName());
			}
		}
		ln("    public " + getName() + "(" + sig + ") {");
		StringBuilder params = new StringBuilder();
		{
			boolean first = true;
			for (ParameterModel parameter : action.getParameters()) {
				if (first) {
					first = false;
				} else {
					params.append(", ");
				}
				params.append(parameter.getName());
			}
		}
		ln("        super(" + params + ");");
		ln("    }");
	}

	@Override
	protected final String getSuperclass() {
		return "G" + action.getName() + "Action";
	}

	@Override
	protected boolean isAbstract() {
		return false;
	}

	@Override
	protected boolean isOverwrite() {
		return false;
	}

}
