package laxstats.query.events;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "gameEntrys", path = "events")
public interface GameQueryRepository extends
		PagingAndSortingRepository<GameEntry, String> {
}
