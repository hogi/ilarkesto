package ilarkesto.gwt.client.animation;

import com.google.gwt.user.client.ui.Widget;

public class DisappearAnimation extends AObservableAnimation {

	private int height;
	private Widget widget;
	private double delayFactor = 1;

	public DisappearAnimation(Widget widget, double delayFactor) {
		this.height = widget.getElement().getClientHeight();
		this.widget = widget;
		this.delayFactor = delayFactor;
	}

	@Override
	protected void onComplete() {
		widget.getElement().getStyle().setProperty("height", "0px");
		fireCompletionEvent();
	}

	@Override
	protected void onStart() {
		widget.getElement().getStyle().setProperty("overflow", "hidden");
	}

	@Override
	protected void onUpdate(double progress) {
		progress *= this.delayFactor;
		progress -= (this.delayFactor - 1);
		if (progress <= 0) {
			progress = 0;
		}
		widget.getElement().getStyle().setProperty("height", (int) ((1 - progress) * this.height) + "px");
	}

	@Override
	public void run(int duration) {
		super.run((int) (duration * this.delayFactor));
	}
}
