package ilarkesto.cli;

public class NoSuchCommandException extends Exception {

	private String command;

	public NoSuchCommandException(String command) {
		super("No such command: " + command);
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

}
