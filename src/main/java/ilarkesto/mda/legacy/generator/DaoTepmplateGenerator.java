package ilarkesto.mda.legacy.generator;

import ilarkesto.mda.legacy.model.EntityModel;

public class DaoTepmplateGenerator extends ABeanGenerator<EntityModel> {

	public DaoTepmplateGenerator(EntityModel bean) {
		super(bean);
	}

	@Override
	protected String getName() {
		return bean.getName() + "Dao";
	}

	@Override
	protected boolean isInterface() {
		return false;
	}

	@Override
	protected void writeContent() {}

	@Override
	protected String getSuperclass() {
		return "G" + bean.getName() + "Dao";
	}

	@Override
	protected boolean isAbstract() {
		return bean.isAbstract();
	}

	@Override
	protected boolean isOverwrite() {
		return false;
	}

}
