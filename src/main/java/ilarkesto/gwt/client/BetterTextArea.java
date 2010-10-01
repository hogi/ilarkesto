package ilarkesto.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextArea;

public class BetterTextArea extends TextArea {

	private int cursorPosition;

	public BetterTextArea() {
		addClickHandler(new MyClickHandler());
		addKeyUpHandler(new MyKeyUpHandler());
	}

	public void wrapSelection(String prefix, String suffix) {
		String text = getText();
		int from = getCursorPos();
		int to = from + getSelectionLength();
		if (from < 0) from = 0;
		if (to > text.length()) to = text.length() - 1;
		if (from > to) {
			int x = to;
			to = from;
			from = x;
		}
		String textPre = text.substring(0, from);
		String textCenter = text.substring(from, to);
		String textPost = text.substring(to);
		text = textPre + prefix + textCenter + suffix + textPost;
		setText(text);
		if (from == to) {
			cursorPosition = from + prefix.length();
		} else {
			cursorPosition = to + prefix.length() + suffix.length();
		}
		setCursorPos(cursorPosition);

		if (isCursorAtBottom()) scrollToBottom();
	}

	private boolean isCursorAtBottom() {
		String text = getText();
		int len = text.length();
		if (len < 500) return false;
		return cursorPosition >= len - 500;
	}

	public void scrollToBottom() {
		getElement().setScrollTop(getElement().getScrollHeight());
	}

	@Override
	public String getSelectedText() {
		String text = getText();
		int from = getCursorPos();
		int to = from + getSelectionLength();
		return text.substring(from, to);
	}

	private void storeCursorPosition() {
		this.cursorPosition = super.getCursorPos();
	}

	@Override
	public int getCursorPos() {
		return cursorPosition;
	}

	private class MyClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			storeCursorPosition();
		}

	}

	private class MyKeyUpHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			storeCursorPosition();
		}
	}

}
