package ilarkesto.io;

import java.io.File;
import java.io.FileFilter;

public class SuffixFileFilter implements FileFilter {

	private String[] suffixes;

	public SuffixFileFilter(String... suffixes) {
		this.suffixes = suffixes;
	}

	@Override
	public boolean accept(File file) {
		for (String suffix : suffixes) {
			if (file.getName().endsWith(suffix)) return true;
		}
		return false;
	}

}
