package ilarkesto.core.service;

public interface ServiceCall {

	void execute(Runnable returnHandler);

	void execute();

}
