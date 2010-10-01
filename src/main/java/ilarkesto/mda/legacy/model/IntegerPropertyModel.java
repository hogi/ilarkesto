package ilarkesto.mda.legacy.model;

public class IntegerPropertyModel extends SimplePropertyModel {

	private int min = 0;
	private int max = Integer.MAX_VALUE;

	public IntegerPropertyModel(BeanModel entityModel, String name) {
		super(entityModel, name, false, false, Integer.class.getName());
	}

	public IntegerPropertyModel setMin(int min) {
		this.min = min;
		return this;
	}

	public IntegerPropertyModel setMax(int max) {
		this.max = max;
		return this;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

}
