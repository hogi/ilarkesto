package ilarkesto.gwt.server;

import ilarkesto.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.di.Context;
import ilarkesto.persistence.DaoService;
import ilarkesto.webapp.AWebApplication;
import ilarkesto.webapp.AWebSession;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Base class for GWT service implementations.
 */
public abstract class AGwtServiceImpl extends RemoteServiceServlet {

	private static final Log LOG = Log.get(AGwtServiceImpl.class);

	protected abstract Class<? extends AWebApplication> getWebApplicationClass();

	protected abstract AWebApplication getWebApplication();

	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		getSession().getContext().createSubContext("gwt-srv");
		super.onBeforeRequestDeserialized(serializedRequest);
	}

	@Override
	protected void doUnexpectedFailure(Throwable t) {
		LOG.error("Service execution failed:", t);
		// getSession().getGwtConversation().getNextData().errors.add("Server error:" +
		// Str.getRootCauseMessage(t));
		super.doUnexpectedFailure(t);
	}

	protected final void handleServiceMethodException(int conversationNumber, String method, Throwable t) {
		LOG.error("Service method failed:", method, "->", t);
		getWebApplication().getTransactionService().cancel();
		try {
			AGwtConversation conversation = getSession().getGwtConversation(conversationNumber);
			conversation.getNextData().addError("Server error:" + Str.getRootCauseMessage(t));
		} catch (Throwable ex) {
			LOG.info(ex);
			return;
		}
	}

	protected final void onServiceMethodExecuted(Context context) {
		// save modified entities
		getWebApplication().getTransactionService().commit();

		// destroy request context
		context.destroy();

		// destroy session, if invalidated
		AWebSession session = getSession();
		if (session.isSessionInvalidated())
			getWebApplication().destroyWebSession(session, getThreadLocalRequest().getSession());
	}

	protected final AWebSession getSession() {
		return getWebApplication().getWebSession(getThreadLocalRequest());
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		AWebApplication.get().autowire(this);
	}

	@Override
	public void destroy() {
		getWebApplication().shutdown();
		super.destroy();
	}

	// --- helper ---

	protected DaoService getDaoService() {
		return getWebApplication().getDaoService();
	}
}
