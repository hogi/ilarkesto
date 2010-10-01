package ilarkesto.gwt.client;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;

public class ImageAnchor extends FocusWidget {

	private AnchorElement a;

	public ImageAnchor(Image image, String text, boolean spacerBetweenImageAndText) {
		if (image == null && text == null) throw new IllegalArgumentException("image or text must be not null");

		a = AnchorElement.as(DOM.createAnchor());
		setElement(a);
		setStyleName("ImageAnchor");

		// a.setHref("javascript:");

		if (image != null) {
			Element img = image.getElement();
			DOM.appendChild(getElement(), img);
		}

		// if (spacerBetweenImageAndText && image != null && text != null) {
		// Element span = DOM.createSpan();
		// span.setInnerHTML("&nbsp;");
		// DOM.appendChild(getElement(), span);
		// }

		if (text != null) {
			Element div = DOM.createDiv();
			div.setClassName("text");
			div.setInnerText(text);
			DOM.appendChild(getElement(), div);
		}

		if (image != null) {
			Element clear = DOM.createDiv();
			clear.setClassName("floatClear");
			DOM.appendChild(getElement(), clear);
		}
	}

	public ImageAnchor(Image image, String text) {
		this(image, text, true);
	}

	public ImageAnchor(Image image) {
		this(image, null, false);
	}

	public void setTooltip(String text) {
		a.setTitle(text);
	}

	public void setHref(String href) {
		a.setHref(href);
	}
}
