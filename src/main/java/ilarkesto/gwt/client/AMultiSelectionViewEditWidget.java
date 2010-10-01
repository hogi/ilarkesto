package ilarkesto.gwt.client;

import ilarkesto.core.base.Utl;

import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public abstract class AMultiSelectionViewEditWidget<I extends Object> extends AViewEditWidget {

	private HTML viewer;
	private MultiSelectionWidget<I> editor;

	@Override
	protected final Widget onViewerInitialization() {
		viewer = new HTML();
		return viewer;
	}

	@Override
	protected final Widget onEditorInitialization() {
		editor = new MultiSelectionWidget<I>();

		ToolbarWidget toolbar = new ToolbarWidget();
		toolbar.addButton(new AAction() {

			@Override
			public String getLabel() {
				return "Apply";
			}

			@Override
			protected void onExecute() {
				submitEditor();
			}
		});
		toolbar.addButton(new AAction() {

			@Override
			public String getLabel() {
				return "Cancel";
			}

			@Override
			protected void onExecute() {
				cancelEditor();
			}
		});

		FlowPanel container = new FlowPanel();
		container.add(editor);
		container.add(toolbar);

		FocusPanel focusPanel = new FocusPanel(container);
		focusPanel.addFocusListener(new EditorFocusListener());

		return focusPanel;
	}

	public final void setViewerItems(Collection items) {
		if (items.isEmpty()) {
			viewer.setText(".");
			return;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object item : items) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(item);
		}
		viewer.setHTML(Utl.concatToHtml(items, "<br>"));
	}

	public void setEditorItems(Collection<I> items) {
		editor.setItems(items);
	}

	public void setEditorSelectedItems(Collection<I> items) {
		editor.setSelected(items);
	}

	public List<I> getEditorSelectedItems() {
		return editor.getSelected();
	}

	private class EditorFocusListener implements FocusListener {

		public void onFocus(Widget sender) {}

		public void onLostFocus(Widget sender) {
			submitEditor();
		}

	}
}
