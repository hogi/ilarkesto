// Copyright (c) 2005 Witoslaw Koczewski, http://www.koczewski.de
package ilarkesto.cli;

public class BooleanOption extends AOption {

	private boolean	set;

	public BooleanOption(String name, String usageText) {
		super(name, usageText);
	}

	public String getUsageSyntax() {
		StringBuffer sb = new StringBuffer();
		sb.append("-");
		sb.append(getName());
		return sb.toString();
	}

	public void setValue(String value) {
		set = true;
	}

	public boolean isSet() {
		return set;
	}

}

// $Log: BooleanOption.java,v $
// Revision 1.1  2005/11/25 17:07:06  wko
// *** empty log message ***
//
// Revision 1.1  2005/07/07 18:00:17  wko
// *** empty log message ***
//
