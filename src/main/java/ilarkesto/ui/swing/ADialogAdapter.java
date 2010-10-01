package ilarkesto.ui.swing;

public abstract class ADialogAdapter<P> {

	public abstract void onSubmit(P payload);

	public void onAbort() {}

}
