package ilarkesto.ui.action;

import ilarkesto.base.Iconized;
import ilarkesto.di.BeanProvider;
import ilarkesto.id.CountingIdGenerator;
import ilarkesto.id.IdGenerator;
import ilarkesto.ui.Option;

import java.util.ArrayList;
import java.util.Collection;

public final class OptionAction<T> extends AAction {

	@Override
	protected void assertPermissions() {}

	@Override
	protected void performAction() throws InterruptedException {
		setAutoShowInfoDone(false);
		showDialog(getOptions(), getMessage());
		if (selectedOption == null) throw new ActionAbortedException();
	}

	public void addOption(Option<T> option) {
		if (options == null) options = new ArrayList<Option<T>>();
		options.add(option);
	}

	public Option<T> getSelectedOption() {
		return selectedOption;
	}

	// --- helper ---

	public boolean isOption(String key) {
		return key.equals(selectedOption.getKey());
	}

	public Option<T> getOption(String key) {
		if (Option.KEY_CANCEL.equals(key)) return null;
		for (Option<T> option : options) {
			if (option.getKey().equals(key)) return option;
		}
		return null;
	}

	private IdGenerator payloadIdGenerator;

	public void addPayloads(Collection<T> payloads) {
		if (payloadIdGenerator == null) payloadIdGenerator = new CountingIdGenerator("p");
		for (T o : payloads) {
			String icon = o instanceof Iconized ? ((Iconized) o).getIcon() : "item";
			addOption(new Option<T>(payloadIdGenerator.generateId(), o.toString(), icon, o));
		}
		horizontal = false;
	}

	public T getSelectedPayload() {
		return getSelectedOption().getPayload();
	}

	public static <T> T showDialog(BeanProvider beanProvider, ActionPerformer actionPerformer, AAction waitingAction,
			String message, Collection<T> payloads) {
		OptionAction<T> action = beanProvider.autowire(new OptionAction());
		action.setHorizontal(false);
		action.addPayloads(payloads);
		action.setMessage(message);
		actionPerformer.performSubAction(action, waitingAction);
		Option<T> option = action.getSelectedOption();
		return option == null ? null : option.getPayload();
	}

	// --- dependencies ---

	private Option<T> selectedOption;

	public final void setSelectedOptionKey(String option) {
		this.selectedOption = getOption(option);
	}

	private Collection<Option<T>> options = new ArrayList<Option<T>>();

	public final Collection<Option<T>> getOptions() {
		return options;
	}

	public final void setOptions(Collection<Option<T>> options) {
		this.options = options;
	}

	private String message;

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	private boolean horizontal = true;

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

}
