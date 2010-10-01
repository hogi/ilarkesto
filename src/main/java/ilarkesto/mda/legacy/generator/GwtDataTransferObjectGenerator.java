package ilarkesto.mda.legacy.generator;

import ilarkesto.base.Str;
import ilarkesto.mda.legacy.model.ApplicationModel;
import ilarkesto.mda.legacy.model.EntityModel;

import java.util.Collection;

public class GwtDataTransferObjectGenerator extends AClassGenerator {

	private ApplicationModel application;
	private Collection<EntityModel> entities;

	public void generate(ApplicationModel application, Collection<EntityModel> entities) {
		this.application = application;
		this.entities = entities;
		generate();
	}

	@Override
	protected String getName() {
		return "GDataTransferObject";
	}

	@Override
	protected String getPackage() {
		return application.getPackageName().replace(".server", ".client");
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
		return getPackage() + ".ADataTransferObject";
	}

	@Override
	protected void writeContent() {
		for (EntityModel entity : entities) {
			String name = entity.getName();
			String type = entity.getPackageName().replace(".server.", ".client.") + "." + name;
			String serverType = entity.getPackageName() + "." + name;
			String nameLower = Str.lowercaseFirstLetter(name);
			String mapVar = nameLower + "s";
			ln();
			comment(name);
			ln();
			ln("    private Map<String, Map>", mapVar + ";");
			ln();
			ln("    public final Collection<Map> get" + name + "s() {");
			ln("        return " + mapVar + " == null ? null : " + mapVar + ".values();");
			ln("    }");
			ln();
			ln("    public final boolean contains" + name + "s() {");
			ln("        return " + mapVar + " != null && !" + mapVar + ".isEmpty();");
			ln("    }");
			ln();
			ln("    public synchronized final void add" + name + "(Map data) {");
			ln("        if (" + mapVar + " == null) " + mapVar + " = new HashMap<String, Map>();");
			ln("        String id = (String) data.get(\"id\");");
			ln("        " + mapVar + ".put(id, data);");
			ln("    }");
			ln();
			ln("    public final void add" + name + "s(List<Map> " + mapVar + ") {");
			ln("        for (Map entity : " + mapVar + ") add" + name + " (entity);");
			ln("    }");
		}
	}
}
