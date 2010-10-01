package ilarkesto.mda.legacy.model;

import java.util.ArrayList;
import java.util.List;

public final class ListPropertyModel extends ACollectionPropertyModel {

	public ListPropertyModel(BeanModel entityModel, String name, boolean reference, Class contentType) {
		super(entityModel, name, reference, false, contentType);
	}

	public ListPropertyModel(BeanModel entityModel, String name, boolean reference, boolean valueObject,
			String contentType) {
		super(entityModel, name, reference, valueObject, contentType);
	}

	@Override
	protected Class getCollectionTypeClass() {
		return List.class;
	}

	@Override
	protected Class getCollectionImplClass() {
		return ArrayList.class;
	}

}
