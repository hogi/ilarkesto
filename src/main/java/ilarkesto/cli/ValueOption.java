// Copyright (c) 2005 Witoslaw Koczewski, http://www.koczewski.de
package ilarkesto.cli;

public class ValueOption extends AOption {

	private String	value;
	private String	valueLabel;

	public ValueOption(String name, String valueLabel, String usageText) {
		super(name, usageText);
		this.valueLabel = valueLabel;
	}

	public String getUsageSyntax() {
		StringBuffer sb = new StringBuffer();
		sb.append("-");
		sb.append(getName());
		sb.append(" <").append(valueLabel).append(">");
		return sb.toString();
	}

	public boolean isSet() {
		return value != null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueLabel() {
		return valueLabel;
	}

	public void setValueLabel(String valueLabel) {
		this.valueLabel = valueLabel;
	}

}

// $Log: ValueOption.java,v $
// Revision 1.2  2006/02/02 17:36:39  wko
// *** empty log message ***
//
// Revision 1.1 2005/11/25 17:07:06 wko
// *** empty log message ***
//
// Revision 1.1 2005/07/07 18:00:17 wko
// *** empty log message ***
//
