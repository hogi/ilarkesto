package ilarkesto.form;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class FileFormField extends AFormField {

    private File value;
    private boolean folder;

    public FileFormField(String name) {
        super(name);
    }

    public void setValue(File value) {
        this.value = value;
    }

    public File getValue() {
        return value;
    }

    public FileFormField setFolder(boolean folder) {
        this.folder = folder;
        return this;
    }

    public boolean isFolder() {
        return folder;
    }

    public String getValueAsString() {
        return value == null ? null : value.getPath();
    }

    public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
        String path = data.get(getName());
        value = path == null ? null : new File(path);
    }

    public void validate() throws ValidationException {
        if (value == null) {
            if (isRequired()) throw new ValidationException("Eingabe erforderlich");
        }
    }

}
