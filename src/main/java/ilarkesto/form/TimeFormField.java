package ilarkesto.form;

import ilarkesto.base.Utl;
import ilarkesto.base.time.Time;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class TimeFormField extends AFormField {

	private String value;

	public TimeFormField(String name) {
		super(name);
	}

	public TimeFormField setValue(Time value) {
		this.value = value == null ? null : value.toString();
		return this;
	}

	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		String newValue = prepareValue(data.get(getName()));
		if (Utl.equals(value, newValue)) return;
		value = newValue;
		fireFieldValueChanged();

	}

	private static String prepareValue(String s) {
		if (s == null) return null;
		s = s.trim();
		if (s.length() == 0) return null;
		try {
			return new Time(s).toString();
		} catch (Throwable ex) {
			return s;
		}
	}

	public void validate() throws ValidationException {
		if (value == null) {
			if (isRequired()) { throw new ValidationException("Eingabe erforderlich"); }
		} else {
			try {
				new Time(value);
			} catch (Throwable ex) {
				throw new ValidationException("Eingabe muss eine Uhrzeit sein. " + ex.getMessage());
			}
		}

	}

	public String getValueAsString() {
		return value;
	}

	public Time getValueAsTime() {
		return value == null ? null : new Time(value);
	}

}
