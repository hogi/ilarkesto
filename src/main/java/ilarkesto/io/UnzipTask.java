package ilarkesto.io;

import ilarkesto.base.Str;
import ilarkesto.concurrent.ATask;
import ilarkesto.core.logging.Log;
import ilarkesto.io.ZipTask.Failure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UnzipTask extends ATask {

    private static final Log LOG = Log.get(UnzipTask.class);

    private File currentFile;
    private List<Failure> failures;
    private int totalFileCount;
    private int fileCount;

    // --- dependencies ---

    private File zipfile;
    private File destination;

    public UnzipTask(File zipfile, File destination) {
        this.zipfile = zipfile;
        this.destination = destination;
    }

    // --- ---

    @Override
    protected void perform() {
        failures = new ArrayList<Failure>();
        LOG.debug("Unzipping", zipfile.getPath(), " -> ", destination);
        IO.unzip(zipfile, destination, new Observer());
    }

    @Override
    public float getProgress() {
        return totalFileCount == 0 ? super.getProgress() : (float) fileCount / (float) totalFileCount;
    }

    @Override
    public String getProgressMessage() {
        return currentFile == null ? null : currentFile.getName();
    }

    class Observer implements IO.UnzipObserver {

        public void onFileCountAvailable(int fileCount) {
            totalFileCount = fileCount;
        }

        public boolean isAbortRequested() {
            return UnzipTask.this.isAbortRequested();
        }

        public void onFileBegin(File f) {
            currentFile = f;
        }

        public void onFileEnd(File f) {
            fileCount++;
        }

        public void onFileError(File f, Throwable ex) {
            Failure failure = new Failure(f, ex);
            failures.add(failure);
            LOG.debug(failure);
        }

    }

    public List<Failure> getFailures() {
        return failures;
    }

    public int getFileCount() {
        return fileCount;
    }

    public static class Failure {

        private File file;
        private Throwable exception;

        public Failure(File file, Throwable ex) {
            this.file = file;
            this.exception = ex;
        }

        public File getFile() {
            return file;
        }

        public Throwable getException() {
            return exception;
        }

        @Override
        public String toString() {
            return file.getName() + ": " + Str.getRootCauseMessage(exception);
        }

    }
}
