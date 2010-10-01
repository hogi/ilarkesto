package ilarkesto.persistence.xstream;

import ilarkesto.email.EmailAddress;

import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;

public class EmailAddressConverter extends AbstractBasicConverter {

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(EmailAddress.class);
	}

	@Override
	protected Object fromString(String str) {
		if (str == null || str.length() == 0) return null;
		return new EmailAddress(str);
	}

}
