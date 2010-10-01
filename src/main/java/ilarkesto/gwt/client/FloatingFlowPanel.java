package ilarkesto.gwt.client;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class FloatingFlowPanel extends Composite implements HasWidgets {

	private FlowPanel panel;
	private String styleName;

	public FloatingFlowPanel() {
		this("FloatingFlowPanel");
	}

	public FloatingFlowPanel(String styleName) {
		super();
		this.styleName = styleName;
		panel = new FlowPanel();
		panel.setStyleName(styleName);
		panel.add(Gwt.createFloatClear());
		initWidget(panel);
	}

	@Override
	public void setStyleName(String style) {
		this.styleName = style;
		panel.setStyleName(style);
	}

	@Override
	public Iterator<Widget> iterator() {
		return panel.iterator();
	}

	@Override
	public void clear() {
		panel.clear();
		panel.add(Gwt.createFloatClear());
	}

	public boolean isEmpty() {
		return panel.getWidgetCount() <= 1;
	}

	@Override
	public void add(Widget w) {
		add(w, false);
	}

	public void add(Widget w, boolean right) {
		SimplePanel element = Gwt.createDiv(styleName + "-element-" + (right ? "right" : "left"), w);
		panel.insert(element, panel.getWidgetCount() - 1);
	}

	public void insert(Widget w, int index) {
		SimplePanel element = Gwt.createDiv(styleName + "-element-left", w);
		panel.insert(element, index);
	}

	@Override
	public boolean remove(Widget w) {
		return panel.remove(w);
	}

	@Override
	public String toString() {
		return Gwt.toString(this);
	}

}
