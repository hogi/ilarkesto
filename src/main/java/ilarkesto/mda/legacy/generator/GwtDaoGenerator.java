package ilarkesto.mda.legacy.generator;

import ilarkesto.base.Str;
import ilarkesto.base.time.Date;
import ilarkesto.base.time.DateAndTime;
import ilarkesto.base.time.Time;
import ilarkesto.core.logging.Log;
import ilarkesto.gwt.client.AGwtDao;
import ilarkesto.gwt.client.AGwtEntity;
import ilarkesto.mda.legacy.model.ApplicationModel;
import ilarkesto.mda.legacy.model.EntityModel;
import ilarkesto.mda.legacy.model.PropertyModel;
import ilarkesto.persistence.AEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class GwtDaoGenerator extends AClassGenerator {

	private ApplicationModel application;
	private Collection<EntityModel> entities;

	public GwtDaoGenerator(ApplicationModel application, Collection<EntityModel> entities) {
		super();
		this.application = application;
		this.entities = new ArrayList<EntityModel>();
		for (EntityModel entity : entities) {
			if (entity.isGwtSupport()) this.entities.add(entity);
		}
	}

	@Override
	protected String getName() {
		return "GDao";
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
		return AGwtDao.class.getName();
	}

	@Override
	protected Set<String> getImports() {
		Set<String> ret = new LinkedHashSet<String>(super.getImports());
		ret.add("scrum.client.common.*");
		ret.add(AGwtDao.class.getPackage().getName() + ".*");
		return ret;
	}

	@Override
	protected void writeContent() {
		for (EntityModel entity : entities) {
			String name = entity.getName();
			String type = entity.getPackageName().replace(".server.", ".client.") + "." + name;
			String nameLower = Str.lowercaseFirstLetter(name);
			String mapVar = nameLower + "s";
			ln();
			comment(name);
			ln();
			ln("    private Map<String, " + type + ">", mapVar + " = new HashMap<String, " + type + ">();");
			ln();
			ln("    public final void clear" + entity.getName() + "s() {");
			ln("        " + Log.class.getName() + ".DEBUG(\"Clearing " + entity.getName() + "s\");");
			ln("        " + mapVar + ".clear();");
			ln("    }");
			ln();
			ln("    public final boolean contains" + name + "(" + type + " " + nameLower + ") {");
			ln("        return " + mapVar + ".containsKey(" + nameLower + ".getId());");
			ln("    }");
			ln();
			ln("    public final void delete" + name + "(" + type + " " + nameLower + ") {");
			ln("        " + mapVar + ".remove(" + nameLower + ".getId());");
			ln("        entityDeleted(" + nameLower + ");");
			ln("    }");
			ln();
			ln("    public final void create" + name + "(" + type + " " + nameLower + ", Runnable successAction) {");
			ln("        " + mapVar + ".put(" + nameLower + ".getId(), " + nameLower + ");");
			ln("        entityCreated(" + nameLower + ", successAction);");
			ln("    }");
			ln();
			ln("    public final void create" + name + "(" + type + " " + nameLower + ") {");
			ln("        " + mapVar + ".put(" + nameLower + ".getId(), " + nameLower + ");");
			ln("        entityCreated(" + nameLower + ", null);");
			ln("    }");
			ln();
			ln("    private final void update" + name + "(Map data) {");
			ln("        String id = (String) data.get(\"id\");");
			ln("        " + type + " entity =", mapVar + ".get(id);");
			ln("        if (entity == null) {");
			ln("            entity = new", type + "(data);");
			ln("            " + mapVar + ".put(id, entity);");
			ln("            " + Log.class.getName() + ".DEBUG(\"" + name
					+ " received: \" + entity.getId() + \" (\"+entity+\")\");");
			ln("        } else {");
			ln("            entity.updateProperties(data);");
			ln("            " + Log.class.getName() + ".DEBUG(\"" + name + " updated: \" + entity);");
			ln("        }");
			ln("        onEntityModifiedRemotely(entity);");
			ln("    }");
			ln();
			ln("    public final", type, "get" + name + "(String id) {");
			ln("        " + type, "ret =", mapVar + ".get(id);");
			ln("        if (ret == null) throw new RuntimeException(\"" + name + " does not exist: \" + id);");
			ln("        return ret;");
			ln("    }");
			ln();
			ln("    public final Set<" + type + "> get" + name + "s(Collection<String> ids) {");
			ln("        Set<" + type + "> ret = new HashSet<" + type + ">();");
			ln("        for (String id : ids) {");
			ln("            " + type + " entity = " + mapVar + ".get(id);");
			ln("            if (entity == null) throw new RuntimeException(\"" + name + " does not exist: \" + id);");
			ln("            ret.add(entity);");
			ln("        }");
			ln("        return ret;");
			ln("    }");
			ln();
			ln("    public final List<" + type + "> get" + name + "s() {");
			ln("        return new ArrayList<" + type + ">(" + mapVar + ".values());");
			ln("    }");
			for (PropertyModel p : entity.getProperties()) {
				String pName = p.getName();
				String pNameUpper = Str.uppercaseFirstLetter(pName);
				String pType = p.getType();
				if (pType.equals(Date.class.getName())) pType = ilarkesto.gwt.client.Date.class.getName();
				if (pType.equals(Time.class.getName())) pType = ilarkesto.gwt.client.Time.class.getName();
				if (pType.equals(DateAndTime.class.getName()))
					pType = ilarkesto.gwt.client.DateAndTime.class.getName();
				if (p.isReference()) {
					if (pType.equals(AEntity.class.getName())) {
						pType = AGwtEntity.class.getName();
					} else {
						pType = pType.replace(".server.", ".client.");
					}
				}
				ln();
				if (p.isCollection()) {

				} else {
					if (p.isUnique()) {
						ln("    public final " + type + " get" + name + "By" + pNameUpper + "(" + pType, pName + ") {");
						ln("        for (" + type + " entity : " + mapVar + ".values()) {");
						ln("            if (entity.is" + pNameUpper + "(" + pName + ")) return entity;");
						ln("        }");
						ln("        return null;");
						ln("    }");
					} else {
						ln("    public final List<" + type + "> get" + name + "sBy" + pNameUpper + "(" + pType, pName
								+ ") {");
						ln("        List<" + type + "> ret = new ArrayList<" + type + ">();");
						ln("        for (" + type + " entity : " + mapVar + ".values()) {");
						ln("            if (entity.is" + pNameUpper + "(" + pName + ")) ret.add(entity);");
						ln("        }");
						ln("        return ret;");
						ln("    }");
					}
				}
			}
		}

		ln();
		ln("    public final void clearAllEntities() {");
		for (EntityModel entity : entities) {
			ln("            clear" + entity.getName() + "s();");
		}
		ln("    }");

		ln();
		ln("    private Collection<Map<String, ? extends AGwtEntity>> entityMaps;");
		ln();
		ln("    @Override");
		ln("    protected final Collection<Map<String, ? extends AGwtEntity>> getEntityMaps() {");
		ln("        if (entityMaps == null) {");
		ln("            entityMaps = new ArrayList<Map<String, ? extends AGwtEntity>>();");
		for (EntityModel entity : entities) {
			ln("            entityMaps.add(" + Str.lowercaseFirstLetter(entity.getName()) + "s);");
		}
		ln("        }");
		ln("        return entityMaps;");
		ln("    }");

		ln();
		ln("    @Override");
		ln("    protected final void updateLocalEntity(String type, Map data) {");
		for (EntityModel entity : entities) {
			ln("        if (type.equals(" + entity.getPackageName().replace(".server.", ".client.") + "."
					+ entity.getName() + ".ENTITY_TYPE)) {");
			ln("            update" + entity.getName() + "(data);");
			ln("            return;");
			ln("        }");
		}
		ln("       throw new RuntimeException(\"Unsupported type: \" + type);");
		ln("    }");

		ln();
		ln("    @Override");
		ln("    public final Map<String, Integer> getEntityCounts() {");
		ln("        Map<String, Integer> ret = new HashMap<String, Integer>();");
		for (EntityModel entity : entities) {
			ln("        ret.put(\"" + entity.getName() + "\", " + Str.lowercaseFirstLetter(entity.getName())
					+ "s.size());");
		}
		ln("        return ret;");
		ln("    }");

		// ln();
		// ln("    @Override");
		// ln("    public void handleDataFromServer(DataTransferObject data) {");
		// ln("        super.handleDataFromServer(data);");
		// for (EntityModel entity : entities) {
		// String name = entity.getName();
		// String type = entity.getPackageModel().toString().replace(".server.", ".client.") + "." + name;
		// String nameLower = Str.lowercaseFirstLetter(name);
		// String mapVar = nameLower + "s";
		// ln();
		// ln("        Collection<Map> " + nameLower + "s = data.get" + name + "s();");
		// ln("        if (" + nameLower + "s != null) update" + name + "s(" + nameLower + "s);");
		// }
		// ln("    }");
	}
}
