package ilarkesto.persistence;

import ilarkesto.auth.AUserDao;
import ilarkesto.base.OverrideExpectedException;
import ilarkesto.search.Searchable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Base class for classes with persistent data.
 */
public abstract class ADatob implements Searchable {

	// --- dependencies ---

	protected static AUserDao userDao;

	public static void setUserDao(AUserDao userDao) {
		ADatob.userDao = userDao;
	}

	// --- ---

	protected abstract ADatobManager getManager();

	public abstract void updateProperties(Map<?, ?> properties);

	protected final void fireModified(String comment) {
		ADatobManager manager = getManager();
		if (manager == null) return;
		manager.onDatobModified(this, comment);
	}

	protected final void repairMissingMaster() {
		ADatobManager manager = getManager();
		if (manager == null) return;
		manager.onMissingMaster(this);
	}

	public boolean matchesKey(String key) {
		return false;
	}

	protected void repairDeadReferences(String entityId) {}

	public void ensureIntegrity() {}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	// --- properties as map ---

	public final Map createPropertiesMap() {
		Map properties = new HashMap();
		storeProperties(properties);
		return properties;
	}

	protected void storeProperties(Map properties) {}

	// public final void updateProperties(Map<?, ?> properties) {
	// for (Map.Entry entry : properties.entrySet()) {
	// updateProperty((String) entry.getKey(), entry.getValue());
	// }
	// }
	//
	// public final void updateProperty(String property, Object value) {
	// if ("id".equals(property)) return;
	//
	// Class type = Reflect.getPropertyType(this, property);
	// if (type == null && property.endsWith("Id")) {
	// property = Str.removeSuffix(property, "Id");
	// type = Reflect.getPropertyType(this, property);
	// if (type == null || !AEntity.class.isAssignableFrom(type))
	// throw new RuntimeException("Unsupported property: " + property);
	// value = ((AEntity) this).getDaoService().getById((String) value);
	// }
	//
	// if (value != null) {
	// if (Date.class.equals(type)) value = new Date((String) value);
	// }
	// Reflect.setProperty(this, property, value);
	// }

	// --- helper ---

	protected static void repairDeadReferencesOfValueObjects(Collection<? extends ADatob> valueObjects, String entityId) {
		for (ADatob vo : valueObjects)
			vo.repairDeadReferences(entityId);
	}

	protected final <S extends AStructure> Set<S> cloneValueObjects(Collection<S> strucktures,
			StructureManager<S> manager) {
		Set<S> ret = new HashSet<S>();
		for (S s : strucktures) {
			ret.add((S) s.clone(manager));
		}
		return ret;
	}

	protected static Set<String> getIdsAsSet(Collection<? extends AEntity> entities) {
		Set<String> result = new HashSet<String>(entities.size());
		for (AEntity entity : entities)
			result.add(entity.getId());
		return result;
	}

	protected static List<String> getIdsAsList(Collection<? extends AEntity> entities) {
		List<String> result = new ArrayList<String>(entities.size());
		for (AEntity entity : entities)
			result.add(entity.getId());
		return result;
	}

	protected static boolean matchesKey(Object object, String key) {
		if (object == null) return false;
		if (object instanceof Searchable) { return ((Searchable) object).matchesKey(key); }
		return object.toString().toLowerCase().indexOf(key) >= 0;
	}

	protected static boolean matchesKey(Collection objects, String key) {
		for (Iterator iter = objects.iterator(); iter.hasNext();) {
			if (matchesKey(iter.next(), key)) return true;
		}
		return false;
	}

	protected void repairDeadDatob(ADatob datob) {
		throw new OverrideExpectedException();
	}

	public class StructureManager<D extends ADatob> extends ADatobManager<D> {

		@Override
		public void onDatobModified(D datob, String comment) {
			fireModified(comment);
		}

		@Override
		public void onMissingMaster(D datob) {
			repairDeadDatob(datob);
		}

		public void ensureIntegrityOfStructures(Collection<? extends AStructure> structures) {
			for (AStructure structure : structures) {
				structure.setManager(this);
				structure.ensureIntegrity();
			}
		}

	}

}
