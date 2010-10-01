package ilarkesto.mda.model;

import java.util.Collections;
import java.util.List;

public class ChildTypeRuleByParentType implements ChildTypeRule {

	private String parentType;
	private List<String> childTypes;

	public ChildTypeRuleByParentType(String parentType, List<String> childTypes) {
		super();
		this.parentType = parentType;
		this.childTypes = childTypes;
	}

	@Override
	public List<String> getAllowedTypes(Node parent) {
		if (parent.isType(parentType)) return childTypes;
		return Collections.emptyList();
	}

}
