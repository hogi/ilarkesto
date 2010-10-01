package ilarkesto.webapp;

import ilarkesto.concurrent.ATask;

public class DestroyTimeoutedSessionsTask extends ATask {

	// --- dependencies ---

	private AWebApplication webApplication;

	public void setWebApplication(AWebApplication webApplication) {
		this.webApplication = webApplication;
	}

	// --- ---

	@Override
	protected void perform() throws InterruptedException {
		webApplication.destroyTimeoutedGwtConversations();
		webApplication.destroyTimeoutedSessions();
	}

}
