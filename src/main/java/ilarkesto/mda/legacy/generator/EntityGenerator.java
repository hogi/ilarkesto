package ilarkesto.mda.legacy.generator;

import ilarkesto.auth.DeleteProtected;
import ilarkesto.auth.EditProtected;
import ilarkesto.auth.Ownable;
import ilarkesto.auth.ViewProtected;
import ilarkesto.base.Str;
import ilarkesto.base.time.Date;
import ilarkesto.base.time.DateAndTime;
import ilarkesto.base.time.Time;
import ilarkesto.mda.legacy.model.EntityModel;
import ilarkesto.mda.legacy.model.PropertyModel;
import ilarkesto.persistence.ADatob;
import ilarkesto.persistence.AEntity;
import ilarkesto.search.Searchable;

import java.util.LinkedHashSet;
import java.util.Set;

public class EntityGenerator extends DatobGenerator<EntityModel> {

	public EntityGenerator(EntityModel bean) {
		super(bean);
	}

	@Override
	protected void writeContent() {
		String daoName = Str.lowercaseFirstLetter(bean.getDaoName());

		if (!bean.isAbstract()) {
			ln();
			comment(AEntity.class.getSimpleName());
			ln();
			s("    public final " + bean.getDaoName() + " getDao() {").ln();
			s("        return " + daoName + ";").ln();
			s("    }").ln();
		}

		ln();
		ln("    protected void repairDeadDatob(" + ADatob.class.getSimpleName() + " datob) {");
		for (PropertyModel p : bean.getProperties()) {
			if (!p.isValueObject()) continue;
			if (p.isCollection()) {
				ln("        if (" + getFieldName(p) + ".contains(datob)) {");
				ln("            " + getFieldName(p) + ".remove(datob);");
				ln("            fireModified(\"" + p.getName() + "-=\" + datob);");
				ln("        }");
			} else {
				ln("        if (valueObject.equals(" + getFieldName(p) + ")) {");
				ln("        " + getFieldName(p) + " = null;");
				ln("            fireModified(\"" + p.getName() + "=null\");");
				ln("        }");
			}
		}
		ln("    }");

		ln();
		ln("    @Override");
		ln("    public void storeProperties(Map properties) {");
		ln("        super.storeProperties(properties);");
		for (PropertyModel p : bean.getProperties()) {
			if (p.isCollection()) {
				String propertyVar = p.isReference() ? p.getName() + "Ids" : p.getName();
				ln("        properties.put(\"" + propertyVar + "\", this." + propertyVar + ");");
			} else {
				String propertyVar = p.isReference() ? p.getName() + "Id" : p.getName();
				if (p.getType().equals(Date.class.getName())) {
					ln("        properties.put(\"" + propertyVar + "\", this." + propertyVar
							+ " == null ? null : this." + propertyVar + ".toString());");
				} else if (p.getType().equals(Time.class.getName())) {
					ln("        properties.put(\"" + propertyVar + "\", this." + propertyVar
							+ " == null ? null : this." + propertyVar + ".toString());");
				} else if (p.getType().equals(DateAndTime.class.getName())) {
					ln("        properties.put(\"" + propertyVar + "\", this." + propertyVar
							+ " == null ? null : this." + propertyVar + ".toString());");
				} else {
					ln("        properties.put(\"" + propertyVar + "\", this." + propertyVar + ");");
				}
			}
		}
		ln("    }");

		// if (bean.isGwtSupport()) {
		// String dtoType = getPackage().replace(".server", ".client") + ".G" + bean.getName() + "Dto";
		// ln();
		// ln("    public " + dtoType + " createDto() {");
		// ln("        " + dtoType + " dto = new " + dtoType + "();");
		// for (PropertyModel p : bean.getProperties()) {
		// if (p.isCollection()) {
		// String propertyVar = p.isReference() ? p.getName() + "Ids" : p.getName();
		// ln("        dto." + propertyVar + ".addAll(this." + propertyVar + ");");
		// } else {
		// String propertyVar = p.isReference() ? p.getName() + "Id" : p.getName();
		// if (p.getType().equals(Date.class.getName())) {
		// ln("        properties.put(\"" + propertyVar + "\", this." + propertyVar
		// + " == null ? null : this." + propertyVar + ".toString());");
		// } else {
		// ln("        properties.put(\"" + propertyVar + "\", this." + propertyVar + ");");
		// }
		// }
		// }
		// ln("        return dto;");
		// ln("    }");
		// }

		if (!bean.isAbstract()) {
			ln();
			ln("    public int compareTo(" + bean.getName() + " other) {");
			ln("        return toString().toLowerCase().compareTo(other.toString().toLowerCase());");
			ln("    }");
		}

		super.writeContent();
	}

	@Override
	protected Set<String> getSuperinterfaces() {
		Set<String> result = new LinkedHashSet<String>();
		result.addAll(super.getSuperinterfaces());
		if (bean.isViewProtected()) result.add(ViewProtected.class.getName() + "<" + getUserClassName() + ">");
		if (bean.isEditProtected()) result.add(EditProtected.class.getName() + "<" + getUserClassName() + ">");
		if (bean.isDeleteProtected()) result.add(DeleteProtected.class.getName() + "<" + getUserClassName() + ">");
		if (bean.isOwnable()) result.add(Ownable.class.getName() + "<" + getUserClassName() + ">");
		if (bean.isSearchable()) result.add(Searchable.class.getName());
		if (!bean.isAbstract()) result.add(Comparable.class.getName() + "<" + bean.getName() + ">");
		return result;
	}

	protected final String getUserClassName() {
		EntityModel userModel = bean.getUserModel();
		if (userModel == null && bean.getName().equals("User")) userModel = bean;
		return userModel.getPackageName() + "." + userModel.getName();
	}

	@Override
	protected void writeDependencies() {
		super.writeDependencies();
		String daoName = Str.lowercaseFirstLetter(bean.getDaoName());
		if (!bean.isAbstract() && !bean.containsDependency(daoName)) {
			dependency(bean.getDaoName(), daoName, true, false);
		}
	}

	@Override
	protected void writeCollectionProperty(PropertyModel p) {
		super.writeCollectionProperty(p);

		// --- isOwner ---
		if ("owners".equals(p.getName()) && bean.isOwnable()) {
			ln();
			ln("    public final boolean isOwner(" + getUserClassName() + " user) {");
			ln("        return " + getFieldName(p) + ".contains(user.getId());");
			ln("    }");
		}

		// --- setOwner ---
		if ("owners".equals(p.getName())) {
			ln();
			ln("    public void setOwner(" + getUserClassName() + " owner) {");
			ln("        clearOwners();");
			ln("        if (owner != null) addOwner((" + p.getContentType() + ")owner);");
			ln("    }");
		}
	}

	@Override
	protected boolean isCopyConstructorEnabled() {
		return false;
	}

}
