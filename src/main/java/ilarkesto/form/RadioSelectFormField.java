package ilarkesto.form;

import ilarkesto.base.MissingDependencyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public final class RadioSelectFormField<T> extends AFormField {

	private List<T> options;
	private T value;
	private boolean required = true;
	private boolean vertical;

	public RadioSelectFormField(String name) {
		super(name);
	}

	public RadioSelectFormField<T> setOptions(Collection<T> options) {
		this.options = new ArrayList<T>(options);
		return this;
	}

	public RadioSelectFormField<T> setValue(T value) {
		this.value = value;
		return this;
	}

	public RadioSelectFormField<T> setVertical(boolean vertical) {
		this.vertical = vertical;
		return this;
	}

	public boolean isVertical() {
		return vertical;
	}

	public List<T> getOptions() {
		if (options == null) throw new MissingDependencyException("options");
		return options;
	}

	public T getValue() {
		return value;
	}

	public String getValueAsString() {
		return value == null ? null : value.toString();
	}

	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		String indexAsString = data.get(getName());
		if (indexAsString == null) {
			value = null;
		} else {
			value = options.get(Integer.parseInt(indexAsString));
		}
	}

	public void validate() throws ValidationException {
		if (value == null && required) throw new ValidationException("Eingabe erforderlich");
	}

}
