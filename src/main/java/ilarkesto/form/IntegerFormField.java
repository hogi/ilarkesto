package ilarkesto.form;


import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class IntegerFormField extends AFormField  {

    private String value;
    private int width = 2;
    private String suffix;

    public IntegerFormField(String name) {
        super(name);
    }

    public IntegerFormField setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public IntegerFormField setWidth(int value) {
        this.width = value;
        return this;
    }

    public IntegerFormField setValue(Integer value) {
        this.value = value == null ? null : String.valueOf(value);
        return this;
    }

    public int getWidth() {
        return width;
    }

    public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
        value = data.get(getName());
        if (value != null) {
            value = value.trim();
        }
        if (value != null && value.length() == 0) {
            value = null;
        }
    }

    public void validate() throws ValidationException {
        if (value == null) {
            if (isRequired()) throw new ValidationException("Eingabe erforderlich");
        } else {
            try {
                Integer.parseInt(value);
            } catch (Exception ex) {
                throw new ValidationException("Hier wird eine Zahl erwartet");
            }
        }
    }

    public String getValueAsString() {
        return value;
    }

    public Integer getValue() {
        return value == null ? null : Integer.parseInt(value);
    }

}
