package ilarkesto.core.diff;

public interface DiffMarker {

	String same(String s);

	String added(String s);

	String removed(String s);

}
