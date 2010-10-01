// Copyright (c) 2005 Witoslaw Koczewski, http://www.koczewski.de
package ilarkesto.cli;

public class BadSyntaxException extends CommandExecutionFailedException {

	public BadSyntaxException(ACommand command, String message) {
		super(command, message);
	}

}

// $Log: BadSyntaxException.java,v $
// Revision 1.2  2006/02/02 17:36:39  wko
// *** empty log message ***
//
// Revision 1.1 2005/11/25 17:07:06 wko
// *** empty log message ***
//
// Revision 1.2 2005/09/23 14:29:36 wko
// *** empty log message ***
//
// Revision 1.1 2005/07/07 18:00:17 wko
// *** empty log message ***
//
