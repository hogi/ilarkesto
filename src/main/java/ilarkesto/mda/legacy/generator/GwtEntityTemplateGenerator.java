package ilarkesto.mda.legacy.generator;

import ilarkesto.mda.legacy.model.BeanModel;

public class GwtEntityTemplateGenerator extends ABeanGenerator<BeanModel> {

	public GwtEntityTemplateGenerator(BeanModel bean) {
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
		toString();
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

	@Override
	protected String getPackage() {
		return super.getPackage().replaceAll(".server.", ".client.");
	}

}
