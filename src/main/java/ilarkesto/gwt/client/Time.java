package ilarkesto.gwt.client;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Time implements Comparable<Time>, Serializable, IsSerializable {

	private int hour;
	private int minute;
	private int second;

	Time() {
		this(new java.util.Date());
	}

	public Time(String timeString) {
		int idx = timeString.indexOf(':');
		if (idx >= 0) {
			hour = Integer.parseInt(timeString.substring(0, idx));
			timeString = timeString.substring(idx + 1);
		} else {
			if (timeString.trim().length() == 0) return; // 0:00:00
			hour = Integer.parseInt(timeString);
			return;
		}
		idx = timeString.indexOf(':');
		if (idx >= 0) {
			minute = Integer.parseInt(timeString.substring(0, idx));
			timeString = timeString.substring(idx + 1);
		} else {
			minute = Integer.parseInt(timeString);
			return;
		}
		second = Integer.parseInt(timeString);
	}

	public Time(java.util.Date javaDate) {
		this.hour = javaDate.getHours();
		this.minute = javaDate.getMinutes();
		this.second = javaDate.getSeconds();
	}

	public static Time now() {
		return new Time(new java.util.Date());
	}

	public long toMillis() {
		return toSeconds() * 1000;
	}

	public long toSeconds() {
		return second + (minute * 60) + (hour * 3600);
	}

	private transient int hashCode;

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = 23;
			hashCode = hashCode * 37 + hour;
			hashCode = hashCode * 37 + minute;
			hashCode = hashCode * 37 + second;
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Time)) return false;
		Time other = (Time) obj;
		return second == other.second && minute == other.minute && hour == other.hour;
	}

	public final boolean isBefore(Time other) {
		return compareTo(other) < 0;
	}

	public final boolean isAfter(Time other) {
		return compareTo(other) > 1;
	}

	public final int compareTo(Time other) {
		if (hour > other.hour) return 1;
		if (hour < other.hour) return -1;
		if (minute > other.minute) return 1;
		if (minute < other.minute) return -1;
		if (second > other.second) return 1;
		if (second < other.second) return -1;
		return 0;
	}

	@Override
	public final String toString() {
		return toString(true);
	}

	public final String toString(boolean includeSeconds) {
		StringBuilder sb = new StringBuilder();
		if (hour < 10) sb.append("0");
		sb.append(hour);
		sb.append(":");
		if (minute < 10) sb.append("0");
		sb.append(minute);
		if (includeSeconds) {
			if (second > 0) {
				sb.append(":");
				if (second < 10) sb.append("0");
				sb.append(second);
			}
		}
		return sb.toString();
	}

}
