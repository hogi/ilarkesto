package ilarkesto.form;

import ilarkesto.base.Factory;
import ilarkesto.base.Reflect;
import ilarkesto.id.CountingIdGenerator;
import ilarkesto.id.IdGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;

public class MultiComplexFormField extends AFormField {

	private Set<Object> value;
	private AddButton addButton;
	private Map<Object, RemoveButton> removeButtons = new HashMap<Object, RemoveButton>();
	private boolean itemsEditable;
	private Map<Object, EditButton> editButtons = new HashMap<Object, EditButton>();
	private Factory itemFactory;
	private Class<? extends BeanForm> elementFormClass;
	private Object uiComponent;

	public MultiComplexFormField(String name, Class<? extends BeanForm> elementFormClass) {
		super(name);
		this.elementFormClass = elementFormClass;

		addButton = (AddButton) new AddButton().setValidateForm(false).setLabel("Hinzuf\u00FCgen...").setIcon("add");
	}

	public BeanForm createSubform() {
		// String formBeanName = getForm().getName() + Str.uppercaseFirstLetter(getName()) + "Form";
		// BeanForm form = (BeanForm) beanProvider.getBean(formBeanName);
		// if (form == null) throw new RuntimeException("Form bean does not exist: " + formBeanName);
		BeanForm form = Reflect.newInstance(elementFormClass);
		form.setStringKeyPrefix(getForm().getStringKeyPrefix());
		return form;
	}

	public Factory getItemFactory() {
		return itemFactory;
	}

	public MultiComplexFormField setItemFactory(Factory factory) {
		this.itemFactory = factory;
		return this;
	}

	public void setItemsEditable(boolean itemsEditable) {
		this.itemsEditable = itemsEditable;
	}

	public AddButton getAddButton() {
		return addButton;
	}

	public RemoveButton getRemoveButton(Object item) {
		return removeButtons.get(item);
	}

	public RemoveButton getRemoveButton(String id) {
		for (RemoveButton button : removeButtons.values()) {
			if (button.getId().equals(id)) return button;
		}
		throw new RuntimeException("remove button does not exist: " + id);
	}

	public EditButton getEditButton(Object item) {
		return editButtons.get(item);
	}

	public EditButton getEditButton(String id) {
		for (EditButton button : editButtons.values()) {
			if (button.getId().equals(id)) return button;
		}
		throw new RuntimeException("edit button does not exist: " + id);
	}

	public MultiComplexFormField setValue(Set<Object> value) {
		this.value = value;
		removeButtons.clear();
		for (Object item : value) {
			RemoveButton button = (RemoveButton) new RemoveButton(item).setLabel("Entfernen").setIcon("delete");
			removeButtons.put(item, button);
		}
		editButtons.clear();
		for (Object item : value) {
			EditButton button = (EditButton) new EditButton(item).setLabel("Bearbeiten").setIcon("edit");
			editButtons.put(item, button);
		}
		return this;
	}

	public void removeValueItem(Object item) {
		value.remove(item);
		setValue(value);
	}

	public void addValueItem(Object item) {
		value.add(item);
		setValue(value);
	}

	public Set<Object> getValue() {
		return value;
	}

	public String getValueAsString() {
		return value == null ? "0" : String.valueOf(value.size());
	}

	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
	// nop
	}

	public void validate() throws ValidationException {
		if (isRequired() && (value == null || value.size() == 0))
			throw new ValidationException("Auswahl erforderlich!");
	}

	public MultiComplexFormField setUiComponent(Object uiComponent) {
		this.uiComponent = uiComponent;
		return this;
	}

	public Object getUiComponent() {
		return uiComponent;
	}

	public class AddButton extends FormButton {

		public AddButton() {
			super(Form.BUTTON_PREFIX + Form.ADD_COMPLEX_BUTTON_NAME_PREFIX + MultiComplexFormField.this.getName());
		}

		public MultiComplexFormField getField() {
			return MultiComplexFormField.this;
		}

	}

	public class RemoveButton extends FormButton {

		private String id;

		private Object item;

		public RemoveButton(Object item) {
			this(item, buttonIdGenerator.generateId());
		}

		private RemoveButton(Object item, String id) {
			super(Form.BUTTON_PREFIX + Form.REMOVE_COMPLEX_BUTTON_NAME_PREFIX + MultiComplexFormField.this.getName()
					+ "_" + id);
			this.item = item;
			this.id = id;

			setValidateForm(false);
		}

		public MultiComplexFormField getField() {
			return MultiComplexFormField.this;
		}

		public Object getItem() {
			return item;
		}

		public String getId() {
			return id;
		}

	}

	public class EditButton extends FormButton {

		private String id;

		private Object item;

		public EditButton(Object item) {
			this(item, buttonIdGenerator.generateId());
		}

		private EditButton(Object item, String id) {
			super(Form.BUTTON_PREFIX + Form.EDIT_COMPLEX_BUTTON_NAME_PREFIX + MultiComplexFormField.this.getName()
					+ "_" + id);
			this.item = item;
			this.id = id;

			setValidateForm(false);
		}

		public MultiComplexFormField getField() {
			return MultiComplexFormField.this;
		}

		public Object getItem() {
			return item;
		}

		public String getId() {
			return id;
		}

	}

	// --- dependencies ---

	private static IdGenerator buttonIdGenerator = new CountingIdGenerator(MultiComplexFormField.class.getSimpleName());

}
