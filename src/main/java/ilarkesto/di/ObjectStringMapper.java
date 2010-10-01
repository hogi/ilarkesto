package ilarkesto.di;

import java.util.Map;

/**
 * Enables converting of Strings to Objects and Objects to Strings.
 */
public interface ObjectStringMapper {

	/**
	 * Indicates if an object type is mappable.
	 */
	boolean isTypeSupported(Class type);

	/**
	 * Map string value to object of specified type.
	 */
	<T extends Object> T stringToObject(String value, Class<T> type);

	/**
	 * Map object to string.
	 */
	String objectToString(Object object);

	/**
	 * Map all object values form given map to string values in new map.
	 */
	Map<String, String> objectsToStrings(Map<String, ? extends Object> map);

	// BeanStorage<Object> createBeanStorageProxy(BeanStorage<String> target);

}
