package ilarkesto.mda.legacy.model;

public abstract class AModel implements Comparable<AModel> {

	private String name;

	public AModel(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int compareTo(AModel o) {
		return name.compareTo(o.name);
	}

}
