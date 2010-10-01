package ilarkesto.form;

import ilarkesto.base.Money;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class MoneyFormField extends AFormField {

	private String value;
	private String currency = Money.EUR;

	public MoneyFormField(String name) {
		super(name);
	}

	public MoneyFormField setValue(Money value) {
		this.value = value == null ? null : value.getAmountAsString(',');
		return this;
	}

	public MoneyFormField setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
		value = data.get(getName());
		if (value != null) {
			value = value.trim();
		}
		if (value != null && value.length() == 0) {
			value = null;
		}
		if (value != null) {
			try {
				setValue(new Money(value, currency));
			} catch (Throwable ex) {}
		}
	}

	public void validate() throws ValidationException {
		if (value == null) {
			if (isRequired()) throw new ValidationException("Eingabe erforderlich");
		} else {
			try {
				new Money(value, currency);
			} catch (Throwable ex) {
				throw new ValidationException("eingabe muss ein betrag sein. " + ex.getMessage());
			}
		}
	}

	public String getValueAsString() {
		return value + ' ' + currency;
	}

	public Money getValueAsMoney() {
		return value == null ? null : new Money(value, currency);
	}

	public String getAmountAsString() {
		return value;
	}
}
