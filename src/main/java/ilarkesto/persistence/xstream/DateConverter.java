package ilarkesto.persistence.xstream;

import ilarkesto.base.time.Date;

import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;

public class DateConverter extends AbstractBasicConverter {

    @Override
    public boolean canConvert(Class type) {
        return type.isAssignableFrom(Date.class);
    }

    @Override
    protected Object fromString(String str) {
        if (str == null || str.length() == 0) return null;
        return new Date(str);
    }

}
