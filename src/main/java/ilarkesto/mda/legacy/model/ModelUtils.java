package ilarkesto.mda.legacy.model;

import ilarkesto.persistence.AEntity;

public class ModelUtils {

    private ModelUtils() {
    // permits instanciation
    }

    public static boolean isEntity(String className) {
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            return true;
        }
        return AEntity.class.isAssignableFrom(clazz);
    }

}
