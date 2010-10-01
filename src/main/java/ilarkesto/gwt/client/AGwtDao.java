package ilarkesto.gwt.client;

import ilarkesto.core.logging.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AGwtDao extends AComponent {

	private String entityIdBase;
	private int entityIdCounter;

	protected abstract Collection<Map<String, ? extends AGwtEntity>> getEntityMaps();

	protected abstract void updateLocalEntity(String type, Map data);

	protected abstract void onEntityModifiedRemotely(AGwtEntity entity);

	protected abstract void onEntityDeletedRemotely(AGwtEntity entity);

	protected abstract void onEntityCreatedLocaly(AGwtEntity entity, Runnable successAction);

	protected abstract void onEntityDeletedLocaly(AGwtEntity entity);

	protected abstract void onEntityPropertyChangedLocaly(AGwtEntity entity, String property, Object value);

	public abstract Map<String, Integer> getEntityCounts();

	public String getEntityIdBase() {
		return entityIdBase;
	}

	public int getEntityIdCounter() {
		return entityIdCounter;
	}

	String getNewEntityId() {
		if (entityIdBase == null) throw new RuntimeException("No entityIdBase received yet.");
		return entityIdBase + "-" + ++entityIdCounter;
	}

	public void handleDataFromServer(ADataTransferObject data) {
		if (data.entityIdBase != null) {
			entityIdBase = data.entityIdBase;
			log.debug("entityIdBase received:", data.entityIdBase);
		}
		if (data.containsEntities()) {
			for (Map entityData : data.getEntities()) {
				updateLocalEntity((String) entityData.get("@type"), entityData);
			}
		}
		if (data.containsDeletedEntities()) {
			List<String> deletedEntities = new ArrayList<String>(data.getDeletedEntities());
			for (Map<String, ? extends AGwtEntity> map : getEntityMaps()) {
				for (String entityId : new ArrayList<String>(deletedEntities)) {
					AGwtEntity entity = map.remove(entityId);
					if (entity != null) {
						deletedEntities.remove(entityId);
						Log.DEBUG("deleted:", entity.getEntityType() + ":", entity);
						onEntityDeletedRemotely(entity);
					}
				}
			}
		}
	}

	protected final void entityCreated(AGwtEntity entity, Runnable successAction) {
		entity.setCreated();
		onEntityCreatedLocaly(entity, successAction);
	}

	protected final void entityDeleted(AGwtEntity entity) {
		onEntityDeletedLocaly(entity);
	}

	public final void entityPropertyChanged(AGwtEntity entity, String property, Object value) {
		onEntityPropertyChangedLocaly(entity, property, value);
	}

	public final AGwtEntity getEntity(String id) {
		for (Map<String, ? extends AGwtEntity> entityMap : getEntityMaps()) {
			AGwtEntity entity = entityMap.get(id);
			if (entity != null) return entity;
		}
		throw new EntityDoesNotExistException(id);
	}

}
