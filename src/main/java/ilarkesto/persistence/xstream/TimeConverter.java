package ilarkesto.persistence.xstream;

import ilarkesto.base.time.Time;

import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;

public class TimeConverter extends AbstractBasicConverter {

    @Override
    public boolean canConvert(Class type) {
        return type.isAssignableFrom(Time.class);
    }

    @Override
    protected Object fromString(String str) {
        if (str == null || str.length() == 0) return null;
        return new Time(str);
    }

}
