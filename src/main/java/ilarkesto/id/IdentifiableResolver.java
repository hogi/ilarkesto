package ilarkesto.id;

import java.util.Collection;
import java.util.List;

public interface IdentifiableResolver<T extends Identifiable> {

	T getById(String id);

	List<T> getByIds(Collection<String> ids);

}
