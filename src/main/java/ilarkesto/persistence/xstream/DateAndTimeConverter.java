package ilarkesto.persistence.xstream;

import ilarkesto.base.time.DateAndTime;

import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;

public class DateAndTimeConverter extends AbstractBasicConverter {

    @Override
    public boolean canConvert(Class type) {
        return type.isAssignableFrom(DateAndTime.class);
    }

    @Override
    protected Object fromString(String str) {
        if (str == null || str.length() == 0) return null;
        return new DateAndTime(str);
    }

}
