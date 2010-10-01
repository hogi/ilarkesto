package ilarkesto.persistence;

public abstract class AStructure extends ADatob {

	private transient ADatob.StructureManager manager;

	public AStructure(AStructure template) {}

	final void setManager(ADatob.StructureManager manager) {
		this.manager = manager;
	}

	@Override
	protected final ADatob.StructureManager getManager() {
		return manager;
	}

	public final AStructure clone(ADatob.StructureManager manager) {
		AStructure result;
		try {
			result = getClass().getConstructor(new Class[] { getClass() }).newInstance(new Object[] { this });
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException("Missing copy constructor in " + getClass(), ex);
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
		result.manager = manager;
		return result;
	}

}
