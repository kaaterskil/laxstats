package laxstats.query.events;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EventQueryRepository extends
		PagingAndSortingRepository<EventEntry, String> {
}
