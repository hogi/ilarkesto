package ilarkesto.form;


import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public interface FormField {

    // --- creating ---

    FormField setLabel(String value);

    FormField setHintText(String value);

    FormField setRequired(boolean required);

    FormField addFormFieldChangeListener(FormFieldChangeListener listener);

    // --- rendering ---

    String getName();

    String getLabel();

    String getHintText();

    boolean isRequired();

    String getErrorMessage();

    // --- rendering + submitting ---

    String getValueAsString();

    // --- submitting ---

    Form getForm();

    void setErrorMessage(String value);

    void update(Map<String, String> data, Collection<FileItem> uploadedFiles);

    void validate() throws ValidationException;

}
