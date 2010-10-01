package ilarkesto.persistence;

import ilarkesto.fp.Predicate;
import ilarkesto.id.IdentifiableResolver;

import java.util.Set;

public interface EntityStore extends IdentifiableResolver<AEntity> {

	void setAlias(String alias, Class cls);

	void load(Class<? extends AEntity> cls, String alias);

	AEntity getEntity(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter);

	int getEntitiesCount(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter);

	Set<AEntity> getEntities(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter);

	void save(AEntity entity);

	void delete(AEntity entity);

}
