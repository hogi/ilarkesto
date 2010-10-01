package ilarkesto.mda.legacy.generator;

import ilarkesto.mda.legacy.model.ComponentModel;

public class ComponentGenerator<B extends ComponentModel> extends ABeanGenerator<B> {

	public ComponentGenerator(B bean) {
		super(bean);
	}

}
