// Copyright (c) 2006 Witoslaw Koczewski, http://www.koczewski.de
package ilarkesto.persistence;


import java.util.EventObject;


public class EntityEvent<E extends AEntity> extends EventObject {

    private E entity;

    public EntityEvent(Object source, E entity) {
        super(source);
        this.entity = entity;
    }

    public E getEntity() {
        return entity;
    }

    public boolean isEntityType(Class<E> type) {
        return type.isAssignableFrom(entity.getClass());
    }

}

// $Log: EntityEvent.java,v $
// Revision 1.2  2006/03/22 19:20:11  wko
// *** empty log message ***
//
// Revision 1.1 2006/02/02 17:36:39 wko
// *** empty log message ***
//
