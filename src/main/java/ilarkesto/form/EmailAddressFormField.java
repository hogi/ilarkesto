package ilarkesto.form;

import ilarkesto.base.Utl;
import ilarkesto.email.EmailAddress;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class EmailAddressFormField extends AFormField {

	private String value;

	public EmailAddressFormField(String name) {
		super(name);
	}

	public EmailAddressFormField setValue(EmailAddress value) {
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
			return new EmailAddress(s).toString();
		} catch (Throwable ex) {
			return s;
		}
	}

	public void validate() throws ValidationException {
		if (value == null) {
			if (isRequired()) throw new ValidationException("Eingabe erforderlich");
		} else {
			try {
				new EmailAddress(value);
			} catch (Throwable ex) {
				throw new ValidationException(ex.getMessage());
			}
		}
	}

	public String getValueAsString() {
		return value;
	}

	public EmailAddress getValueAsEmailAddress() {
		return value == null ? null : new EmailAddress(value);
	}

}
