package ilarkesto.base.time;

import java.util.Calendar;
import java.util.Locale;

public enum Weekday {
	MONDAY(Calendar.MONDAY), TUESDAY(Calendar.TUESDAY), WEDNESDAY(Calendar.WEDNESDAY), THURSDAY(Calendar.THURSDAY), FRIDAY(
			Calendar.FRIDAY), SATURDAY(Calendar.SATURDAY), SUNDAY(Calendar.SUNDAY);

	private final int dayOfWeek;

	Weekday(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public int getDayOfWeekAssumeMondayIs1st() {
		int ret = dayOfWeek - 1;
		if (ret == 0) ret = 7;
		return ret;
	}

	public static Weekday get(int dayOfWeek) {
		for (Weekday weekday : Weekday.values()) {
			if (weekday.dayOfWeek == dayOfWeek) return weekday;
		}
		throw new RuntimeException("Weekday does not exist: " + dayOfWeek);
	}

	public boolean isWeekend() {
		return this == SATURDAY || this == SUNDAY;
	}

	public boolean isWorkday() {
		return !isWeekend();
	}

	public String getLabel(Locale locale) {
		// TODO internationalization
		switch (this) {
			case MONDAY:
				return "Montag";
			case TUESDAY:
				return "Dienstag";
			case WEDNESDAY:
				return "Mittwoch";
			case THURSDAY:
				return "Donnerstag";
			case FRIDAY:
				return "Freitag";
			case SATURDAY:
				return "Samstag";
			case SUNDAY:
				return "Sonntag";
		}
		throw new RuntimeException("fatal enum error in " + getClass().getName());
	}

}
