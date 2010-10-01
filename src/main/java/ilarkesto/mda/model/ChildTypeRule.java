package ilarkesto.mda.model;

import java.util.List;

public interface ChildTypeRule {

	List<String> getAllowedTypes(Node parent);

}
