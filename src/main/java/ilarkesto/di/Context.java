package ilarkesto.di;

import java.util.Set;

/**
 * Represents a context in which a thread is running. Contexts can be nested.
 */
public final class Context {

	private static final ThreadLocal<Context> THREAD_LOCAL = new ThreadLocal<Context>();

	private static Context rootContext;

	private Context parent;
	private String name;
	private MultiBeanProvider beanProvider;

	private Context(Context parent, String name) {
		this.parent = parent;
		this.name = name;

		beanProvider = new MultiBeanProvider();
		if (parent != null) beanProvider.addBeanProvider(parent.beanProvider);
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final void addBeanProvider(Object heanProvider) {
		this.beanProvider.addBeanProvider(heanProvider);
	}

	public final BeanProvider getBeanProvider() {
		return this.beanProvider;
	}

	public final Context getParentContext() {
		return parent;
	}

	public final Context createSubContext(String name) {
		Context context = new Context(this, name);
		// LOG.debug("Context created:", context);
		context.bindCurrentThread();
		return context;
	}

	public final void destroy() {
		releaseCurrentThread();
		if (parent != null) {
			parent.bindCurrentThread();
		}
	}

	public final void bindCurrentThread() {
		THREAD_LOCAL.set(this);
		Thread.currentThread().setName(toString());
	}

	private final void releaseCurrentThread() {
		THREAD_LOCAL.set(null);
		Thread.currentThread().setName("<no context>");
	}

	@Override
	public final String toString() {
		return parent == null ? name : parent + " > " + name;
	}

	public static Context getRootContext() {
		if (rootContext == null) throw new RuntimeException("Root context does not exist. Call createRootContext()");
		return rootContext;
	}

	public static synchronized Context createRootContext(String name) {
		if (rootContext != null) throw new RuntimeException("Root context already exists: " + rootContext);
		rootContext = new Context(null, name);
		rootContext.bindCurrentThread();
		return rootContext;
	}

	public static Context get() {
		Context context = THREAD_LOCAL.get();
		if (context == null) context = getRootContext();
		return context;
	}

	// --- helper ---

	public final <T> T autowire(T target) {
		return beanProvider.autowire(target);
	}

	public final void autowireClass(Class type) {
		beanProvider.autowireClass(type);
	}

	/**
	 * Gets all beans by their type. All beans instanceof the given type are returned.
	 */
	public final <T> Set<T> getBeansByType(Class<T> type) {
		return beanProvider.getBeansByType(type);
	}

	/**
	 * Provides a set of all existing bean names.
	 */
	public final Set<String> getBeanNames() {
		return beanProvider.beanNames();
	}

	/**
	 * Gets a bean by name.
	 */
	public final <T> Object getBean(String beanName) {
		return beanProvider.getBean(beanName);
	}

}
