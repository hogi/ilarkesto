package ilarkesto.locale;

import ilarkesto.base.Str;
import ilarkesto.persistence.AEntity;

import java.util.Locale;

/**
 * Provides localized strings for a specified locale.
 */
public abstract class Localizer {

	public abstract String string(String key, Object... parameters);

	public abstract Locale getLocale();

	public final String string(AEntity scope, String key, Object... parameters) {
		return string("entity." + scope.getDao().getEntityName() + "." + key, parameters);
	}

	public final String string(Class scope, String key, Object... parameters) {
		if (scope == null) return string(key, parameters);
		if (AEntity.class.isAssignableFrom(scope))
			return string("entity." + Str.lowercaseFirstLetter(scope.getSimpleName()) + "." + key, parameters);
		return string(key, parameters);
	}

}
