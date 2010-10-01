package ilarkesto.concurrent.locker;

public class LockingException extends Exception {

	private Lock lock;

	public LockingException(Lock lock) {
		super(lock.toString());
		this.lock = lock;
	}

	public Lock getLock() {
		return lock;
	}

}
