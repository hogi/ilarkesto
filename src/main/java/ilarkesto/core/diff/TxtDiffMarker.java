package ilarkesto.core.diff;

public class TxtDiffMarker implements DiffMarker {

	@Override
	public String same(String s) {
		return s;
	}

	@Override
	public String added(String s) {
		return "[+" + s + "]";
	}

	@Override
	public String removed(String s) {
		return "[-" + s + "]";
	}

}
