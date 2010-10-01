package ilarkesto.form;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class HiddenFormField implements FormField {

    private String name;
    private String value;
    private Form form;
    private boolean required;

    public FormField addFormFieldChangeListener(FormFieldChangeListener listener) {
        throw new RuntimeException("Not implemented yet!");
    }

    public boolean isRequired() {
        return required;
    }

    public HiddenFormField setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public HiddenFormField(String name) {
        this.name = name;
    }

    public FormField setLabel(String value) {
        // nop;
        return this;
    }

    public FormField setHintText(String value) {
        // nop;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return name;
    }

    public String getHintText() {
        return null;
    }

    public String getErrorMessage() {
        return null;
    }

    public String getValueAsString() {
        return value;
    }

    public void setErrorMessage(String value) {
    // nop
    }

    public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
        value = data.get(name);
    }

    public void validate() {}

}
