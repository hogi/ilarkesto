package ilarkesto.cli;

public abstract class AOption {

	public abstract void setValue(String value) throws BadSyntaxException;

	public abstract String getUsageSyntax();

	private String name;
	private String usageText;

	public AOption(String name, String usageText) {
		this.name = name;
		this.usageText = usageText;
	}

	public String getUsageDescription() {
		return usageText;
	}

	public void setUsageText(String usageText) {
		this.usageText = usageText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
