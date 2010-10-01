package ilarkesto.persistence.xstream;

import ilarkesto.base.time.TimePeriod;

import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;

public class TimePeriodConverter extends AbstractBasicConverter {

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(TimePeriod.class);
	}

	@Override
	protected Object fromString(String str) {
		if (str == null || str.length() == 0) return null;
		return new TimePeriod(str);
	}
}
