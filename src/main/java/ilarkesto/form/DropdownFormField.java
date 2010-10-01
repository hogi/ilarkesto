package ilarkesto.form;

import ilarkesto.base.Str;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.fileupload.FileItem;

public class DropdownFormField<E> extends AFormField {

	private E value;
	private SortedMap<String, E> selectableItems;
	private boolean localizeItems;

	public DropdownFormField(String name) {
		super(name);
	}

	public DropdownFormField<E> setLocalizeItems(boolean localizeItems) {
		this.localizeItems = localizeItems;
		return this;
	}

	public boolean isLocalizeItems() {
		return localizeItems;
	}

	public DropdownFormField<E> setSelectableItems(Collection<E> items) {
		List<E> itemList = (List<E>) (items instanceof List ? items : new ArrayList<E>(items));
		if (!itemList.isEmpty() && itemList.get(0) instanceof Comparable) Collections.sort((List) itemList);
		this.selectableItems = new TreeMap<String, E>();
		int i = 0;
		for (E item : itemList) {
			String key = Str.fillUpLeft(String.valueOf(i++), "0", 4);
			selectableItems.put(key, item);
		}
		return this;
	}

	public Map<String, E> getSelectableItems() {
		return selectableItems;
	}

	public DropdownFormField<E> setValue(E value) {
		this.value = value;
		return this;
	}

	public E getValue() {
		return value;
	}

	public String getValueAsString() {
		return value == null ? null : value.toString();
	}

	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		value = selectableItems.get(data.get(getName()));
	}

	public void validate() throws ValidationException {
		if (value == null && isRequired()) throw new ValidationException("Eingabe erforderlich.");
	}

}
