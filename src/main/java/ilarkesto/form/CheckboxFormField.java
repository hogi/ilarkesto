package ilarkesto.form;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class CheckboxFormField extends AFormField {

    private boolean checked;

    public CheckboxFormField(String name) {
        super(name);
    }

    public CheckboxFormField setChecked(boolean value) {
        this.checked = value;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getValueAsString() {
        return String.valueOf(checked);
    }

    public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
        checked = data.containsKey(getName());
    }

    public void validate() {}

}
