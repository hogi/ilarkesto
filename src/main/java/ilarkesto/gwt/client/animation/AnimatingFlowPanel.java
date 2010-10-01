package ilarkesto.gwt.client.animation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class AnimatingFlowPanel<W extends Widget> extends Composite implements HasWidgets {

	private static boolean animationsDisabled = false;

	private FlowPanel panel;
	private boolean actionRunning;
	private List<Runnable> actions = new LinkedList<Runnable>();
	private double animationDelayFactor = 2;

	public AnimatingFlowPanel() {
		panel = new FlowPanel();
		initWidget(panel);
	}

	public AnimatingFlowPanel(double animationDelayFactor) {
		this();
		this.animationDelayFactor = animationDelayFactor;
	}

	private void executeNextAction() {
		if (actionRunning) return;
		if (actions.isEmpty()) return;
		Runnable action = actions.get(0);
		actions.remove(action);
		action.run();
	}

	private void execute(Runnable action) {
		actions.add(action);
		executeNextAction();
	}

	public void insertAnimated(int index, W widget, Integer height, Runnable runAfter) {
		if (animationsDisabled) {
			insert(index, widget);
			return;
		}
		execute(new InsertAction(index, widget, height, runAfter));
	}

	public void insert(int index, W widget) {
		if (actionRunning) {
			execute(new InsertAction(index, widget));
		} else {
			if (index < 0) index = panel.getWidgetCount();
			panel.insert(widget, index);
		}
	}

	public boolean remove(final Widget widget) {
		if (actionRunning) {
			execute(new RemoveAction(widget));
		} else {
			panel.remove(widget);
		}
		return true;
	}

	public void removeAnimated(final W widget) {
		if (animationsDisabled) {
			remove(widget);
			return;
		}
		execute(new RemoveAction(widget));
	}

	public void clear() {
		panel.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return panel.iterator();
	}

	@Override
	public void add(Widget w) {
		insertAnimated(-1, (W) w, null, null);
	}

	public void setAnimationDelayFactor(double animationDelayFactor) {
		this.animationDelayFactor = animationDelayFactor;
	}

	private class RemoveAction implements Runnable {

		private Widget widget;
		private boolean animated;

		public RemoveAction(Widget widget) {
			this.widget = widget;
			this.animated = true;
		}

		@Override
		public void run() {
			if (!animated) {
				panel.remove(widget);
				return;
			}

			actionRunning = true;
			DisappearAnimation animation = new DisappearAnimation(widget, animationDelayFactor);
			animation.addCompletionListener(new CompletionListener() {

				@Override
				public void completionEvent(AObservableAnimation source) {
					panel.remove(widget);
					actionRunning = false;
					executeNextAction();
				}
			});
			animation.run(250);

		}

	}

	private class InsertAction implements Runnable {

		private Widget widget;
		private int index;
		private boolean animated;
		private Integer animationHeight;
		private Runnable runAfter;

		public InsertAction(int index, Widget widget, Integer animationHeight, Runnable runAfter) {
			this.index = index;
			this.widget = widget;
			this.animationHeight = animationHeight;
			this.runAfter = runAfter;
			this.animated = true;
		}

		public InsertAction(int index, Widget widget) {
			this.index = index;
			this.widget = widget;
			this.animated = false;
		}

		@Override
		public void run() {
			if (index < 0) index = panel.getWidgetCount();

			if (!animated) {
				panel.insert(widget, index);
				return;
			}

			actionRunning = true;
			AppearAnimation animation = new AppearAnimation(animationHeight, widget, animationDelayFactor);
			panel.insert(widget, index);
			animation.addCompletionListener(new CompletionListener() {

				@Override
				public void completionEvent(AObservableAnimation source) {
					actionRunning = false;
					if (runAfter != null) runAfter.run();
					executeNextAction();
				}
			});
			animation.run(250);
		}

	}

	public static interface MoveObserver {

		void onMoved();
	}

}
