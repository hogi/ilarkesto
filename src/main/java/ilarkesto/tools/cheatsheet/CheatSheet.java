package ilarkesto.tools.cheatsheet;

import java.util.ArrayList;
import java.util.List;

public class CheatSheet {

	private String label;
	private String description;
	private List<CheatGroup> groups = new ArrayList<CheatGroup>();

	public CheatSheet(String label) {
		super();
		this.label = label;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addGroup(CheatGroup group) {
		groups.add(group);
	}

	public List<CheatGroup> getGroups() {
		return groups;
	}

	public String getDescription() {
		return description;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}

}
