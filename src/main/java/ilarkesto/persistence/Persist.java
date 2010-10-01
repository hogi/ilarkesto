package ilarkesto.persistence;

import ilarkesto.auth.AUser;
import ilarkesto.auth.Auth;
import ilarkesto.fp.Predicate;
import ilarkesto.id.Identifiable;
import ilarkesto.search.Searchable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class Persist {

	public static List<String> getIdsAsList(Collection<? extends Identifiable> entities) {
		List<String> ret = new ArrayList<String>(entities.size());
		for (Identifiable entity : entities) {
			ret.add(entity.getId());
		}
		return ret;
	}

	public static List<Map> createPropertiesMaps(Collection<? extends AEntity> entities) {
		List<Map> result = new ArrayList<Map>(entities.size());
		for (AEntity entity : entities) {
			result.add(entity.createPropertiesMap());
		}
		return result;
	}

	public static boolean test(AEntity entity, Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		if (typeFilter != null && !typeFilter.test(entity.getClass())) return false;
		if (entityFilter != null && !entityFilter.test(entity)) return false;
		return true;
	}

	public static boolean matchesKeys(Searchable e, Collection<String> keys) {
		for (String key : keys) {
			if (!e.matchesKey(key)) return false;
		}
		return true;
	}

	public static List<AEntity> getVisible(Collection<AEntity> entities, AUser user) {
		List<AEntity> result = new ArrayList<AEntity>(entities.size());
		for (AEntity entity : entities)
			if (Auth.isVisible(entity, user)) result.add(entity);
		return result;
	}

}
