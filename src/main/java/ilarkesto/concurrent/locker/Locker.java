package ilarkesto.concurrent.locker;

import ilarkesto.base.Str;
import ilarkesto.base.time.TimePeriod;

import java.util.HashMap;
import java.util.Map;

public class Locker {

	private Map<Object, Lock> locks = new HashMap<Object, Lock>();

	public void lock(Object object, Object locker, boolean allowRelockBySameLocker, TimePeriod lockTime)
			throws LockingException {
		if (object == null) throw new IllegalArgumentException("object == null");
		synchronized (locks) {
			Lock lock = locks.get(object);
			if (lock != null) {
				if (lock.isTimedOut()) {
					locks.remove(object);
				} else {
					if (allowRelockBySameLocker && lock.getLocker() == locker) {
						// locked by locker
						return;
					}
					throw new LockingException(lock);
				}
			}
			lock = new Lock(object, locker, lockTime);
			locks.put(object, lock);
		}
	}

	public void unlock(Object object) {
		synchronized (locks) {
			locks.remove(object);
		}
	}

	@Override
	public String toString() {
		synchronized (locks) {
			return Str.format(locks.values());
		}
	}

}
