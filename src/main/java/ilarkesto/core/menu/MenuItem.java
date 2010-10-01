package ilarkesto.core.menu;

public interface MenuItem {

	String getLabel();

	boolean isSelected();

	void select();

	void deselect();

}
