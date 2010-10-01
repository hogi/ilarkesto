package ilarkesto.base;

/**
 * Data type for storing amount of bytes.
 */
public class Bytes implements Comparable<Bytes> {

	private long bytes;

	public Bytes(long bytes) {
		this.bytes = bytes;
	}

	public long toLong() {
		return bytes;
	}

	@Override
	public String toString() {
		return String.valueOf(bytes) + " Bytes";
	}

	public String toRoundedString() {
		if (bytes > 10000000000l) return String.valueOf(Math.round(bytes / 1000000000f)) + " GB";
		if (bytes > 10000000) return String.valueOf(Math.round(bytes / 1000000f)) + " MB";
		if (bytes > 10000) return String.valueOf(Math.round(bytes / 1000f)) + " KB";
		return toString();
	}

	public static Bytes kilo(long kilobytes) {
		return new Bytes(kilobytes * 1000);
	}

	public static Bytes mega(long megabytes) {
		return new Bytes(megabytes * 1000000);
	}

	public static Bytes giga(long gigabytes) {
		return new Bytes(gigabytes * 1000000000);
	}

	@Override
	public int compareTo(Bytes o) {
		if (bytes == o.bytes) return 0;
		return bytes > o.bytes ? 1 : -1;
	}
}
