package ilarkesto.mda.legacy.model;

public class StringPropertyModel extends SimplePropertyModel {

	private boolean richtext;
	private Integer maxLenght;
	private boolean templateAvailable;

	public StringPropertyModel(BeanModel entityModel, String name) {
		super(entityModel, name, false, false, String.class.getName());
	}

	public StringPropertyModel setRichtext(boolean multiline) {
		this.richtext = multiline;
		return this;
	}

	public boolean isRichtext() {
		return richtext;
	}

	public StringPropertyModel setMaxLenght(Integer maxLenght) {
		this.maxLenght = maxLenght;
		return this;
	}

	public Integer getMaxLenght() {
		return maxLenght;
	}

	public boolean isMaxLengthSet() {
		return maxLenght != null;
	}

	public StringPropertyModel setTemplateAvailable(boolean template) {
		this.templateAvailable = template;
		return this;
	}

	public boolean isTemplateAvailable() {
		return templateAvailable;
	}

}
