package ilarkesto.form;

import ilarkesto.richtext.RichTextFormatter;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public class OutputFormField extends AFormField {

	private String	text;

	public OutputFormField(String name) {
		super(name);
	}

	public OutputFormField setText(String text) {
		this.text = text;
		return this;
	}

	public String getHtml() {
		if (text == null) return "";
		String html = RichTextFormatter.toHtml(text);
		return html;
	}

	public String getValueAsString() {
		return text;
	}

	public void update(Map<String, String> data, Collection<FileItem> uploadedFiles) {
	// nop
	}

	public void validate() {}

}
