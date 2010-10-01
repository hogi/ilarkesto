package ilarkesto.mda.legacy.generator;

import ilarkesto.mda.legacy.model.BeanModel;

public class BeanTemplateGenerator<E extends BeanModel> extends ABeanGenerator<E> {

	public BeanTemplateGenerator(E bean) {
		super(bean);
	}

	@Override
	protected final String getName() {
		return bean.getName();
	}

	@Override
	protected final boolean isInterface() {
		return false;
	}

	@Override
	protected void writeContent() {

	}

	@Override
	protected final String getSuperclass() {
		return "G" + bean.getName();
	}

	@Override
	protected final boolean isAbstract() {
		return bean.isAbstract();
	}

	@Override
	protected boolean isOverwrite() {
		return false;
	}

}
