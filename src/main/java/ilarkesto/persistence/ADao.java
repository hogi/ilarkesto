package ilarkesto.persistence;

import ilarkesto.auth.AUser;
import ilarkesto.auth.AUserDao;
import ilarkesto.auth.Auth;
import ilarkesto.base.Reflect;
import ilarkesto.base.Utl;
import ilarkesto.base.time.DateAndTime;
import ilarkesto.core.logging.Log;
import ilarkesto.di.Context;
import ilarkesto.fp.Predicate;
import ilarkesto.id.IdentifiableResolver;
import ilarkesto.search.SearchResultsConsumer;
import ilarkesto.search.Searchable;
import ilarkesto.search.Searcher;
import ilarkesto.base.Iconized;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ADao<E extends AEntity> extends ADatobManager<E> implements IdentifiableResolver<E>, Searcher,
		DaoListener, Iconized {

	private static final Log LOG = Log.get(ADao.class);

	private Predicate<Class> entityTypeFilter;
	private String icon;

	// --- ---

	@Override
	public void onDatobModified(E entity, String comment) {
		entity.setLastModified(DateAndTime.now());

		// don's save new entities
		if (!isPersistent(entity)) return;

		LOG.info("Entity modified:", Utl.toStringWithType(entity), "->", comment);
		saveEntity(entity);
	}

	@Override
	public void onMissingMaster(E entity) {
		deleteEntity(entity);
		throw new EnsureIntegrityCompletedException();
	}

	// --- basic ---

	public abstract String getEntityName();

	public abstract Class getEntityClass();

	public Map<String, Class> getAliases() {
		return Collections.emptyMap();
	}

	private boolean isPersistent(E entity) {
		return transactionService.isPersistent(entity.getId());
	}

	public final Predicate<Class> getEntityTypeFilter() {
		if (entityTypeFilter == null) {
			entityTypeFilter = new Predicate<Class>() {

				public boolean test(Class parameter) {
					return parameter.isAssignableFrom(getEntityClass());
				}

			};
		}
		return entityTypeFilter;
	}

	@Override
	public String getIcon() {
		if (icon == null) {
			icon = (String) Reflect.getFieldValue(getEntityClass(), "ICON");
			if (icon == null) icon = getEntityName();
		}
		return icon;
	}

	public int getEntitiesCount(Predicate<E> predicate) {
		return transactionService.getEntitiesCount(getEntityTypeFilter(), (Predicate<AEntity>) predicate);
	}

	public E getEntity(Predicate<E> predicate) {
		return (E) transactionService.getEntity(getEntityTypeFilter(), (Predicate<AEntity>) predicate);
	}

	public final Set<E> getEntities(Predicate<E> filter) {
		// long start = System.currentTimeMillis();
		Set<E> result = (Set<E>) transactionService.getEntities(getEntityTypeFilter(), (Predicate<AEntity>) filter);
		// long time = System.currentTimeMillis() - start;
		// if (time > 2000) throw new RuntimeException("getEntities took too
		// long. fix it!");
		return result;
	}

	@Override
	public E getById(String id) {
		if (id == null) throw new RuntimeException("id must not be null");
		E entity = (E) transactionService.getById(id);
		if (entity == null) throw new EntityDoesNotExistException(id);
		return entity;
	}

	@Deprecated
	public E getEntityById(String id) {
		return getById(id);
	}

	@Override
	public List<E> getByIds(Collection<String> entitiesIds) {
		Set<String> ids = new HashSet<String>(entitiesIds);
		List<E> result = (List<E>) transactionService.getByIds(entitiesIds);
		if (result.size() != ids.size()) {
			for (E entity : result) {
				ids.remove(entity.getId());
			}
			throw new EntityDoesNotExistException((String) ids.toArray()[0]);
		}
		return result;
	}

	public Set<E> getByIdsAsSet(Collection<String> entitiesIds) {
		return new HashSet<E>(getByIds(entitiesIds));
	}

	@Deprecated
	public List<E> getEntitiesByIds(Collection<String> entitiesIds) {
		return getByIds(entitiesIds);
	}

	public Set<E> getEntitiesVisibleForUser(final AUser user) {
		return getEntities(new Predicate<E>() {

			public boolean test(E e) {
				return Auth.isVisible(e, user);
			}

		});
	}

	public Set<E> getEntities() {
		return (Set<E>) transactionService.getEntities(getEntityTypeFilter(), null);
	}

	public void deleteEntity(E entity) {
		transactionService.deleteEntity(entity);
		daoService.fireEntityDeleted(entity);
	}

	public void saveEntity(E entity) {
		transactionService.saveEntity(entity);
		daoService.fireEntitySaved(entity);
	}

	public E newEntityInstance() {
		E entity;
		try {
			entity = (E) getEntityClass().newInstance();
		} catch (InstantiationException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
		entity.setLastModified(DateAndTime.now());
		transactionService.registerEntity(entity);
		return entity;
	}

	public E newEntityInstance(String id) {
		E entity = newEntityInstance();
		entity.setId(id);
		return entity;
	}

	public void ensureIntegrity() {
		if (!initialized) throw new RuntimeException("Not initialized!");
		Class clazz = getEntityClass();
		LOG.info("Ensuring integrity:", clazz.getSimpleName());
		for (E entity : getEntities()) {
			try {
				entity.ensureIntegrity();
			} catch (EnsureIntegrityCompletedException ex) {
				continue;
			} catch (Throwable ex) {
				throw new RuntimeException("Ensuring integrity for " + clazz.getSimpleName() + ":" + entity.getId()
						+ " failed.", ex);
			}
		}
	}

	public void entityDeleted(EntityEvent event) {
		AEntity entity = event.getEntity();
		for (AEntity e : getEntities()) {
			try {
				e.repairDeadReferences(entity.getId());
			} catch (EnsureIntegrityCompletedException ex) {
				continue;
			}
		}
	}

	public void entitySaved(EntityEvent event) {}

	public void feed(final SearchResultsConsumer searchBox) {
		if (!Searchable.class.isAssignableFrom(getEntityClass())) return;

		for (AEntity entity : getEntities(new Predicate<E>() {

			public boolean test(E e) {
				return Auth.isVisible(e, searchBox.getSearcher()) && e instanceof Searchable
						&& Persist.matchesKeys(e, searchBox.getKeys());
			}

		})) {
			searchBox.addEntity(entity);
		}

	}

	protected final TransactionService getTransactionService() {
		return transactionService;
	}

	// ---

	protected Set<Class> getValueObjectClasses() {
		return Collections.emptySet();
	}

	@Override
	public String toString() {
		String entityName = getEntityName();
		if (entityName == null) return entityName + "Dao";
		return getClass().getName();
	}

	// --------------------
	// --- dependencies ---
	// --------------------

	private volatile boolean initialized;

	public synchronized final void initialize(Context context) {
		if (initialized) throw new RuntimeException("Already initialized!");

		Class entityClass = getEntityClass();
		context.autowireClass(entityClass);
		for (Class c : getValueObjectClasses())
			context.autowireClass(c);
		Field daoField;
		try {
			daoField = entityClass.getDeclaredField("dao");
			boolean accessible = daoField.isAccessible();
			if (!accessible) daoField.setAccessible(true);
			try {
				daoField.set(null, this);
			} catch (IllegalArgumentException ex) {
				throw new RuntimeException(ex);
			} catch (IllegalAccessException ex) {
				throw new RuntimeException(ex);
			} catch (NullPointerException ex) {
				throw new RuntimeException("Setting dao field failed. Is it static?", ex);
			}
			if (!accessible) daoField.setAccessible(false);
		} catch (SecurityException ex) {
			throw new RuntimeException(ex);
		} catch (NoSuchFieldException ex) {
			// nop
		}

		initialized = true;
	}

	private DaoService daoService;

	public final void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	public final DaoService getDaoService() {
		return daoService;
	}

	private TransactionService transactionService;

	public final void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	protected AUserDao userDao;

	public final void setUserDao(AUserDao userDao) {
		this.userDao = userDao;
	}

}
