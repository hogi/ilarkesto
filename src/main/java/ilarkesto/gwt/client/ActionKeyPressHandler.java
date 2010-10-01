package ilarkesto.gwt.client;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

public class ActionKeyPressHandler implements KeyPressHandler {

	private AAction action;
	private int[] keyCodes;
	private boolean ctrl;

	public ActionKeyPressHandler(AAction action, boolean ctrl, int... keyCodes) {
		super();
		this.action = action;
		this.keyCodes = keyCodes;
		this.ctrl = ctrl;
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		if (ctrl && !event.isControlKeyDown()) return;
		for (int keyCode : keyCodes) {
			if (keyCode == event.getCharCode()) {
				action.execute();
				event.stopPropagation();
				return;
			}
		}
	}

}
