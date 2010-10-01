package ilarkesto.tools.cheatsheet;

public class Cheat {

	private String command;
	private String label;

	public Cheat(String command, String label) {
		super();
		this.command = command;
		this.label = label;
	}

	public String getCommand() {
		return command;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return command + " -> " + label;
	}

}
