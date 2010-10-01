package ilarkesto.tools.cheatsheet;

import java.util.ArrayList;
import java.util.List;

public class CheatGroup {

	private String label;
	private List<Cheat> cheats = new ArrayList<Cheat>();

	public CheatGroup(String label) {
		super();
		this.label = label;
	}

	public List<Cheat> getCheats() {
		return cheats;
	}

	public void addCheat(Cheat cheat) {
		cheats.add(cheat);
	}

	@Override
	public String toString() {
		return label;
	}

}
