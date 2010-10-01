package ilarkesto.di.app;

import ilarkesto.properties.APropertiesStore;

public abstract class AApplicationConfig {

	protected APropertiesStore p;

	public AApplicationConfig(APropertiesStore p) {
		this.p = p;
	}
}
