package ilarkesto.persistence;

import java.io.File;

public interface EntityfilePreparator {

    public void prepareEntityfile(File file, Class type, String alias);

}
