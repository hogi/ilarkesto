package ilarkesto.ui.usermessage;

import ilarkesto.base.time.DateAndTime;

public class UserMessage {

	public static final String INFO = "info";
	public static final String ERROR = "error";
	public static final String WARN = "warn";

	private DateAndTime dateAndTime;
	private String type;
	private String text;

	public UserMessage(String type, String text) {
		this.type = type;
		this.text = text;
		this.dateAndTime = DateAndTime.now();
	}

	public boolean isError() {
		return ERROR.equals(getType());
	}

	public boolean isWarn() {
		return WARN.equals(getType());
	}

	public boolean isInfo() {
		return INFO.equals(getType());
	}

	public String getText() {
		return text;
	}

	public String getType() {
		return type;
	}

	public DateAndTime getDateAndTime() {
		return dateAndTime;
	}

	@Override
	public String toString() {
		return type + " " + text;
	}

}
