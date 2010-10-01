package ilarkesto.mda.legacy.model;

import java.util.HashSet;
import java.util.Set;

public final class SetPropertyModel extends ACollectionPropertyModel {

	public SetPropertyModel(BeanModel entityModel, String name, boolean reference, Class contentType) {
		super(entityModel, name, reference, false, contentType);
	}

	public SetPropertyModel(BeanModel entityModel, String name, boolean reference, boolean valueObject,
			String contentType) {
		super(entityModel, name, reference, valueObject, contentType);
	}

	@Override
	protected Class getCollectionTypeClass() {
		return Set.class;
	}

	@Override
	protected Class getCollectionImplClass() {
		return HashSet.class;
	}

}
