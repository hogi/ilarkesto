package ilarkesto.sync;

public class SyncProfile {

	private int lastFileCount;

	// --- dependencies ---

	private String name;
	private SyncSource left;
	private SyncSource right;

	public SyncProfile(String name, SyncSource left, SyncSource right) {
		this.name = name;
		this.left = left;
		this.right = right;
	}

	public SyncProfile(String name, String left, String right) {
	// this(name,null,null);
	}

	// --- ---

	public void setName(String name) {
		this.name = name;
	}

	public SyncSource getLeft() {
		return left;
	}

	public SyncSource getRight() {
		return right;
	}

	// public String getDestinationPath() {
	// return destinationPath;
	// }

	public int getLastFileCount() {
		return lastFileCount;
	}

	public void setLastFileCount(int lastFileCount) {
		this.lastFileCount = lastFileCount;
	}

	public void setRight(SyncSource right) {
		this.right = right;
	}

	public void setLeft(SyncSource left) {
		this.left = left;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
