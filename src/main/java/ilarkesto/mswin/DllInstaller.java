package ilarkesto.mswin;

import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;

import java.io.File;
import java.io.IOException;

public class DllInstaller {

    private static final Log LOG = Log.get(DllInstaller.class);

    private DllInstaller() {}

    public static void installDll(String dllName, boolean deleteOnExit) {
        File file = new File(dllName + ".dll").getAbsoluteFile();
        try {
            IO.copyResource("dll/" + dllName + ".dll", file.getPath());
            LOG.info(dllName, "installed to", file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (deleteOnExit) file.deleteOnExit();
    }

}
