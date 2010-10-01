package ilarkesto.persistence;

import ilarkesto.base.Sys;

import java.util.Comparator;

public class LastModifiedEntityComparator<E extends AEntity> implements Comparator<E> {
    
    public static final LastModifiedEntityComparator INSTANCE = new LastModifiedEntityComparator();

    public int compare(AEntity a, AEntity b) {
        return -Sys.compare(a.getLastModified(), b.getLastModified());
    }

}
