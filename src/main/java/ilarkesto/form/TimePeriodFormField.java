package ilarkesto.form;

import ilarkesto.base.Utl;
import ilarkesto.base.time.TimePeriod;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class TimePeriodFormField extends AFormField {

	private String value;

	public TimePeriodFormField(String name) {
		super(name);
	}

	public TimePeriodFormField setValue(TimePeriod value) {
		this.value = value == null ? null : value.toHoursAndMinutesString();
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
		if (s.indexOf(':') < 0) s += ":00";
		try {
			return new TimePeriod(s).toHoursAndMinutesString();
		} catch (Throwable ex) {
			return s;
		}
	}

	public void validate() throws ValidationException {
		if (value == null) {
			if (isRequired()) { throw new ValidationException("Eingabe erforderlich"); }
		} else {
			try {
				new TimePeriod(value);
			} catch (Throwable ex) {
				throw new ValidationException("Eingabe muss eine Uhrzeit sein. " + ex.getMessage());
			}
		}

	}

	public String getValueAsString() {
		return value;
	}

	public TimePeriod getValueAsTimePeriod() {
		return value == null ? null : new TimePeriod(value);
	}

}
