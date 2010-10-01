package ilarkesto.ui.action;

import ilarkesto.concurrent.TaskManager;
import ilarkesto.core.logging.Log;
import ilarkesto.di.BeanProvider;
import ilarkesto.di.Context;
import ilarkesto.ui.AUi;

import java.util.HashMap;
import java.util.Map;

public final class ActionPerformer {

	private static final Log LOG = Log.get(ActionPerformer.class);

	private static Map<String, AAction> actions = new HashMap<String, AAction>();

	/**
	 * Start or continue the given action.
	 */
	public AAction triggerAction(String actionId, AUi ui, BeanProvider userParameters) {
		AAction action = actions.get(actionId);
		if (action == null) {
			try {
				action = createAction(actionId);
			} catch (ClassNotFoundException ex) {
				return null;
			}
			if (action == null) return null;
			startAction(action, ui, userParameters);
		} else {
			continueAction(action, ui, userParameters);
		}
		return action;
	}

	static void unregisterAction(String actionId) {
		if (actions == null) return;
		if (actionId == null) return;
		actions.remove(actionId);
	}

	static void registerAction(AAction action) {
		if (actions == null) return;
		if (action == null) return;
		actions.put(action.getActionId(), action);
	}

	public void performSubAction(AAction action, AAction waitingAction) {
		action.setParentAction(waitingAction);
		autowireAction(action, waitingAction.getUi(), null);
		action.setUi(waitingAction.getUi());
		action.run();

		// action.setParentAction(waitingAction);
		// startAction(action, waitingAction.getUi(), null);
		// try {
		// action.waitForFinish();
		// } catch (InterruptedException ex) {
		// throw new ActionAbortedException("InterruptedException");
		// }

		Throwable exception = action.getException();
		if (exception != null) {
			if (exception instanceof RuntimeException) throw (RuntimeException) exception;
			throw new RuntimeException(exception);
		}
	}

	private AAction createAction(String actionId) throws ClassNotFoundException {
		Class<AAction> actionClass;
		actionClass = getActionClass(actionId);
		if (actionClass == null) return null;
		try {
			return actionClass.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void startAction(AAction action, AUi ui, BeanProvider userParameters) {
		LOG.debug("Starting action:", action);
		autowireAction(action, ui, userParameters);
		action.setUi(ui);
		taskManager.start(action);
	}

	private void continueAction(AAction action, AUi ui, BeanProvider userParameters) {
		LOG.debug("Continuing action:", action);
		autowireAction(action, ui, userParameters);
		synchronized (action) {
			action.notifyAll();
		}
	}

	private void autowireAction(AAction action, AUi ui, BeanProvider userParameters) {
		if (action == null) return;
		if (userParameters != null) userParameters.autowire(action);
		Context.get().autowire(action);
		action.setUi(ui);
		autowireAction(action.getParentAction(), ui, userParameters); // autowire parents recursively
	}

	private Class<AAction> getActionClass(String actionId) throws ClassNotFoundException {
		return (Class<AAction>) Class.forName(actionId);
	}

	// --- dependencies ---

	private TaskManager taskManager;

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

}
