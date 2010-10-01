package ilarkesto.form;

public interface FormFieldChangeListener<F extends FormField> {

	public void fieldValueChanged(F field);

}
