package ilarkesto.persistence;

public interface DaoListener<E extends AEntity> {

	void entitySaved(EntityEvent<E> event);

	void entityDeleted(EntityEvent<E> event);

}
