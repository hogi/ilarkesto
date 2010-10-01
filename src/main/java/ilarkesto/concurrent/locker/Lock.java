package ilarkesto.concurrent.locker;

import ilarkesto.base.time.DateAndTime;
import ilarkesto.base.time.TimePeriod;

public class Lock {

	private Object object;
	private Object locker;
	private DateAndTime time;
	private TimePeriod maxLockTime;

	Lock(Object object, Object locker, TimePeriod maxLockTime) {
		this.object = object;
		this.locker = locker;
		this.maxLockTime = maxLockTime;

		this.time = DateAndTime.now();
	}

	public Object getObject() {
		return object;
	}

	public Object getLocker() {
		return locker;
	}

	public DateAndTime getTime() {
		return time;
	}

	public boolean isTimedOut() {
		return time.getPeriodToNow().isGreaterThen(maxLockTime);
	}

	@Override
	public String toString() {
		return object + " locked by " + locker + " since " + time;
	}

}
