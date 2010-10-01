package ilarkesto.gwt.client.animation;

import com.google.gwt.user.client.ui.Widget;

public class AppearAnimation extends AObservableAnimation {

	private Widget widget;
	private int height;
	private double delayFactor = 1;

	public AppearAnimation(Integer height, Widget widget, double delayFactor) {
		this.height = height == null ? 20 : height;
		this.widget = widget;
		this.delayFactor = delayFactor;
		widget.getElement().getStyle().setProperty("visible", "false");
		widget.getElement().getStyle().setProperty("height", "0px");
		widget.getElement().getStyle().setProperty("marginTop", "0px");
		widget.getElement().getStyle().setProperty("marginBottom", "0px");
	}

	@Override
	protected void onStart() {
		widget.getElement().getStyle().setProperty("overflow", "hidden");
		widget.getElement().getStyle().setProperty("visible", "true");
	}

	@Override
	protected void onComplete() {
		widget.getElement().getStyle().setProperty("height", "auto");
		widget.getElement().getStyle().setProperty("overflow", "auto");
		fireCompletionEvent();
	}

	@Override
	protected void onUpdate(double progress) {
		progress *= this.delayFactor;
		progress -= (this.delayFactor - 1);
		if (progress <= 0) {
			progress = 0;
		}
		widget.getElement().getStyle().setProperty("height", (int) (progress * this.height) + "px");
	}

	@Override
	public void run(int duration) {
		super.run((int) (duration * this.delayFactor));
	}
}