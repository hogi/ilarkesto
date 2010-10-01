package ilarkesto.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.HyperlinkImpl;

public class HyperlinkWithoutHistory extends Widget implements HasHTML, HasClickHandlers {

	private static HyperlinkImpl impl = GWT.create(HyperlinkImpl.class);

	private final Element anchorElem = DOM.createAnchor();

	public HyperlinkWithoutHistory() {
		this(DOM.createDiv());
	}

	protected HyperlinkWithoutHistory(Element elem) {
		if (elem == null) {
			setElement(anchorElem);
		} else {
			setElement(elem);
			DOM.appendChild(getElement(), anchorElem);
		}

		sinkEvents(Event.ONCLICK);
		setStyleName("gwt-Hyperlink");
	}

	@Deprecated
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addHandler(handler, ClickEvent.getType());
	}

	public String getHTML() {
		return DOM.getInnerHTML(anchorElem);
	}

	public String getText() {
		return DOM.getInnerText(anchorElem);
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if (DOM.eventGetType(event) == Event.ONCLICK && impl.handleAsClick(event)) {
			DOM.eventPreventDefault(event);
		}
	}

	public void setHTML(String html) {
		DOM.setInnerHTML(anchorElem, html);
	}

	public void setText(String text) {
		DOM.setInnerText(anchorElem, text);
	}

	@Override
	protected void onEnsureDebugId(String baseID) {
		ensureDebugId(anchorElem, "", baseID);
		ensureDebugId(getElement(), baseID, "wrapper");
	}
}
