package ilarkesto.search;

import ilarkesto.auth.AUser;
import ilarkesto.persistence.AEntity;

import java.util.Set;

public interface SearchResultsConsumer {

	AUser getSearcher();

	Set<String> getKeys();

	// TODO abstract from entity
	void addEntity(AEntity entity);

}
