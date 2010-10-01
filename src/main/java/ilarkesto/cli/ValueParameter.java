// Copyright (c) 2005 Witoslaw Koczewski, http://www.koczewski.de
package ilarkesto.cli;

public class ValueParameter extends AParameter {

	private String	value;

	public ValueParameter(String name, String description) {
		super(name, description);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public boolean isSet() {
		return value != null;
	}

}

// $Log: ValueParameter.java,v $
// Revision 1.2  2006/02/02 17:36:39  wko
// *** empty log message ***
//
// Revision 1.1 2005/11/25 17:07:06 wko
// *** empty log message ***
//
// Revision 1.1 2005/07/07 18:00:17 wko
// *** empty log message ***
//
