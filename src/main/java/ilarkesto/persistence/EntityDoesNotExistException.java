package ilarkesto.persistence;

public class EntityDoesNotExistException extends RuntimeException {

    private String entityId;

    public EntityDoesNotExistException(String entityId) {
        super("Entity does not exist: " + entityId);
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }

}
