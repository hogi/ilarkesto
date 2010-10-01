// Copyright (c) 2006 Witoslaw Koczewski, http://www.koczewski.de
package ilarkesto.cli;

public class CommandExecutionFailedException extends Exception {

	private ACommand	command;

	public CommandExecutionFailedException(ACommand command, String message) {
		super(message);
		this.command = command;
	}

	public ACommand getCommand() {
		return this.command;
	}

}

// $Log: CommandExecutionFailedException.java,v $
// Revision 1.1  2006/02/02 17:36:39  wko
// *** empty log message ***
//
