package ilarkesto.core.diff;

import java.util.List;

public interface DiffTokenizer {

	List<String> tokenize(String s);

	String concat(List<String> tokens);
	
}
