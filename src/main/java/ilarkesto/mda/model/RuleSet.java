package ilarkesto.mda.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleSet implements NodeTypes {

	private List<ChildTypeRule> childTypeRules = new ArrayList<ChildTypeRule>();

	RuleSet() {
		addChildTypeRuleByParentType(Root, GwtModule, EntitySet);
		addChildTypeRuleByParentType(EntitySet, Package);
		addChildTypeRuleByParentType(GwtModule, Package, TextBundle);
		addChildTypeRuleByParentType(Package, Entity, Datastruct, JavaClass, Component, Event, ServiceCall);
		addChildTypeRuleByParentType(TextBundle, Text);
		addChildTypeRuleByParentType(Text, EN, DE);
		addChildTypeRuleByParentType(ServiceCall, Parameter);
		addChildTypeRuleByParentType(Entity, TextProperty, IntegerProperty, FloatProperty, BooleanProperty,
			DateProperty, TimeProperty, DateAndTimeProperty, ReferenceProperty);
		addChildTypeRuleByParentType(Component, Dependency, InitializationProcedure);
		addChildTypeRuleByParentType(Dependency, Type, Inject);
		addChildTypeRuleByParentType(Event, Parameter, Flag);
		addChildTypeRuleByParentType(Parameter, Type, Index);
	}

	public List<String> getAllowedChildTypes(Node parent) {
		if (parent == null) return Collections.emptyList();
		Set<String> types = new HashSet<String>();
		for (ChildTypeRule rule : childTypeRules) {
			types.addAll(rule.getAllowedTypes(parent));
		}
		return new ArrayList(types);
	}

	public boolean containsAllowedChildTypes(Node parent) {
		if (parent == null) return false;
		return !getAllowedChildTypes(parent).isEmpty();
	}

	public void addChildTypeRuleByParentType(String parentType, String... childTypes) {
		addChildTypeRule(new ChildTypeRuleByParentType(parentType, Arrays.asList(childTypes)));
	}

	public void addChildTypeRule(ChildTypeRule rule) {
		childTypeRules.add(rule);
	}
}
