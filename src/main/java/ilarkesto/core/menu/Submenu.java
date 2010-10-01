package ilarkesto.core.menu;

public interface Submenu<I extends MenuItem> extends MenuItem {

	Menu<I> getMenu();

}
