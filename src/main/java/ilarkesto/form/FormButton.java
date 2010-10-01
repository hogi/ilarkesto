package ilarkesto.form;

public class FormButton  {

    private String name;
    private String label;
    private boolean updateFields = true;
    private boolean validateForm = true;
    private Character accessKey;
    private String icon;

    public FormButton(String name) {
        this.name = name;
        this.label = name;
    }

    public FormButton setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public FormButton setAccessKey(Character accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public Character getAccessKey() {
        return accessKey;
    }

    public FormButton setLabel(String value) {
        this.label = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public boolean isUpdateFields() {
        return updateFields;
    }

    public FormButton setUpdateFields(boolean updateFields) {
        this.updateFields = updateFields;
        return this;
    }

    public boolean isValidateForm() {
        return validateForm;
    }

    public FormButton setValidateForm(boolean validateForm) {
        this.validateForm = validateForm;
        return this;
    }

    public boolean isAbort() {
        return Form.ABORT_BUTTON_NAME.equals(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
