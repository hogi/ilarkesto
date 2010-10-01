package ilarkesto.form;


import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;

public class ItemFormField<T> extends AFormField {

    private T value;

    private ClearButton clearButton;

    private SelectButton selectButton;

    private Collection<T> selectableItems;

    private boolean autoTrigger;

    public ItemFormField(String name) {
        super(name);

        clearButton = (ClearButton) new ClearButton(Form.BUTTON_PREFIX + Form.CLEAR_ITEM_BUTTON_NAME_PREFIX + name)
                .setValidateForm(false).setLabel("Entfernen").setIcon("delete");

        selectButton = (SelectButton) new SelectButton(Form.BUTTON_PREFIX + Form.SELECT_ITEM_BUTTON_NAME_PREFIX + name)
                .setValidateForm(false).setLabel("Auswahl...").setIcon("edit");
    }

    public ItemFormField setAutoTrigger(boolean autoTrigger) {
        this.autoTrigger = autoTrigger;
        return this;
    }

    public boolean isAutoTrigger() {
        return autoTrigger && value == null;
    }

    public ItemFormField setSelectableItems(Collection<T> items) {
        this.selectableItems = items;
        return this;
    }

    public Collection getSelectableItems() {
        Set<T> result = new HashSet<T>(selectableItems);
        if (value != null) result.remove(value);
        return result;
    }

    public ClearButton getClearButton() {
        if (isRequired() || value == null) return null;
        return clearButton;
    }

    public SelectButton getSelectButton() {
        return selectButton;
    }

    public ItemFormField setValue(T value) {
        this.value = value;
        fireFieldValueChanged();
        return this;
    }

    public T getValue() {
        return value;
    }

    public String getValueAsString() {
        return value == null ? null : value.toString();
    }

    public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {}

    public void validate() throws ValidationException {
        if (isRequired() && value == null) throw new ValidationException("Hier ist eine Auswahl erforderlich.");
    }

    public class SelectButton extends FormButton {

        public SelectButton(String name) {
            super(name);
        }

        public ItemFormField getField() {
            return ItemFormField.this;
        }

    }

    public class ClearButton extends FormButton {

        public ClearButton(String name) {
            super(name);
        }

        public ItemFormField getField() {
            return ItemFormField.this;
        }

    }

}
