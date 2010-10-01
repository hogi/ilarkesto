package ilarkesto.io;


import java.io.File;
import java.util.Iterator;

/**
 * Iterator for files that includes sub dirs.
 */
public class FileIterator implements Iterator<File> {

	private File[] files;
	private int index = 0;
	private File currentFile;
	private FileIterator iterator;

	public void setFiles(File[] files) {
		this.files = files;
	}

	public void setDir(File file) {
		setFiles(file.listFiles());
	}

	public boolean hasNext() {
		if (iterator != null) {
			boolean hasNext = iterator.hasNext();
			if (hasNext) return true;
			iterator = null;
		}
		if (files == null) return false;
		if (index >= files.length) return false;
		return true;
	}

	public File next() {
		if (iterator != null) return iterator.next();
		currentFile = files[index++];
		if (currentFile.isDirectory()) {
			iterator = new FileIterator();
			iterator.setFiles(currentFile.listFiles());
			if (hasNext()) return next();
		}
		return currentFile;
	}

	public void remove() {
		IO.delete(currentFile);
	}

}
