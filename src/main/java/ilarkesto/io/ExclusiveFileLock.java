package ilarkesto.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class ExclusiveFileLock {

	private File file;
	private FileLock lock;

	public ExclusiveFileLock(File file) throws FileLockedException {
		this.file = file;

		file.getParentFile().mkdirs();

		FileChannel channel;
		try {
			channel = new RandomAccessFile(file, "rw").getChannel();
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		try {
			lock = channel.tryLock();
		} catch (OverlappingFileLockException ex) {
			lock = null;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		if (lock == null) throw new FileLockedException();
	}

	public void release() {
		try {
			lock.release();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public class FileLockedException extends Exception {

		private FileLockedException() {
			super("File already locked: " + file.getName());
		}
	}

}
