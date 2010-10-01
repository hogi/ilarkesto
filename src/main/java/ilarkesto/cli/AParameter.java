package ilarkesto.cli;

public abstract class AParameter {

	public abstract void setValue(String value) throws BadSyntaxException;

	private String name;
	private String description;

	public AParameter(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setName(String usageText) {
		this.name = usageText;
	}

}
