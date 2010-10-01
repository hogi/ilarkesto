package ilarkesto.core.scope;

import ilarkesto.core.base.Utl;

public class ScopeUtil {

	public static String getComponentName(Class componentType) {
		String name = Utl.getSimpleName(componentType);
		name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
		return name;
	}

	public static String getComponentSimpleClassName(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

}
