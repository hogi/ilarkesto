package ilarkesto.persistence.xstream;

import ilarkesto.base.Money;

import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;

public class MoneyConverter extends AbstractBasicConverter {

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(Money.class);
	}

	@Override
	protected Object fromString(String str) {
		if (str == null || str.length() == 0) return null;
		return new Money(str);
	}

}
