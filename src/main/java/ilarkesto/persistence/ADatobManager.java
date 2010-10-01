package ilarkesto.persistence;

public abstract class ADatobManager<D extends ADatob> {

	/**
	 * Called by the datob, when it is modified
	 */
	public abstract void onDatobModified(D datob, String comment);

	public abstract void onMissingMaster(D datob);

}
