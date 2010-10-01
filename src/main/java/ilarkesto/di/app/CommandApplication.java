package ilarkesto.di.app;

import ilarkesto.cli.ACommand;
import ilarkesto.cli.BadSyntaxException;
import ilarkesto.cli.CommandExecutionFailedException;
import ilarkesto.cli.CommandService;
import ilarkesto.core.logging.Log;

public class CommandApplication extends ACommandLineApplication {

	private static final Log LOG = Log.get(CommandApplication.class);

	// --- dependencies ---

	private Class<? extends ACommand> commandClass;

	public void setCommandClass(Class<? extends ACommand> commandClass) {
		this.commandClass = commandClass;
	}

	// --- ---

	@Override
	protected int execute(String[] args) {
		ACommand command;
		try {
			command = commandClass.newInstance();
		} catch (InstantiationException ex1) {
			throw new RuntimeException(ex1);
		} catch (IllegalAccessException ex1) {
			throw new RuntimeException(ex1);
		}
		autowire(command);
		Object result;
		try {
			result = CommandService.execute(command, args);
		} catch (BadSyntaxException ex) {
			System.out.println("Bad Syntax: " + ex.getMessage());
			System.out.println("Syntax:\n\n" + command.getUsage());
			return 1;
		} catch (CommandExecutionFailedException ex) {
			throw new RuntimeException(ex);
		}
		if (result != null) {
			System.out.println(result);
		}
		return 0;
	}

}
