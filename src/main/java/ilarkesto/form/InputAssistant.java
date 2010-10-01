package ilarkesto.form;

import java.util.Set;

public interface InputAssistant<T> {

	Set<T> getOptions();

	String applyToInput(String input, T selectedOption);

}
