package laxstats.query.events;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "events", path = "events")
public interface EventQueryRepository extends
		PagingAndSortingRepository<EventEntry, String> {
}
