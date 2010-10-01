package ilarkesto.mda.legacy.generator;

import ilarkesto.core.base.Str;
import ilarkesto.mda.legacy.model.ActionModel;
import ilarkesto.mda.legacy.model.ParameterModel;

import java.util.Collection;
import java.util.List;

public class GwtActionGenerator extends AClassGenerator {

	private ActionModel action;

	public GwtActionGenerator(ActionModel action) {
		super();
		this.action = action;
	}

	@Override
	protected final String getName() {
		return "G" + action.getName() + "Action";
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
		writeInstanceVariables();
		writeConstructor();
		writeIsExecutable();
		List<ParameterModel> parameters = action.getParameters();
		if (!parameters.isEmpty()) writeGetId(parameters);
	}

	private void writeInstanceVariables() {
		ln();
		for (ParameterModel parameter : action.getParameters()) {
			ln("    protected " + parameter.getType().replace(".server", ".client") + " " + parameter.getName() + ";");
		}
	}

	private void writeConstructor() {
		ln();
		StringBuilder sig = new StringBuilder();
		boolean first = true;
		for (ParameterModel parameter : action.getParameters()) {
			if (first) {
				first = false;
			} else {
				sig.append(", ");
			}
			sig.append(parameter.getType().replace(".server", ".client")).append(" ").append(parameter.getName());
		}
		ln("    public " + getName() + "(" + sig + ") {");
		for (ParameterModel parameter : action.getParameters()) {
			ln("        this." + parameter.getName() + " = " + parameter.getName() + ";");
		}
		ln("    }");
	}

	private void writeIsExecutable() {
		ln();
		ln("    @Override");
		ln("    public boolean isExecutable() {");
		ln("        return true;");
		ln("    }");
	}

	private void writeGetId(Collection<ParameterModel> parameters) {
		StringBuilder params = new StringBuilder();
		boolean first = true;
		for (ParameterModel parameter : parameters) {
			if (first) {
				first = false;
			} else {
				params.append(", ");
			}
			params.append(parameter.getName());
		}
		ln();
		ln("    @Override");
		ln("    public String getId() {");
		ln("        return " + Str.class.getName() + ".getSimpleName(getClass()) + '_' + " + Str.class.getName()
				+ ".toHtmlId(" + params + ");");
		ln("    }");
	}

	@Override
	protected final String getSuperclass() {
		return "scrum.client.common.AScrumAction";
	}

	@Override
	protected final boolean isAbstract() {
		return true;
	}

	@Override
	protected boolean isOverwrite() {
		return true;
	}

}
