package laxstats.query.events;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "plays", path = "plays")
public interface PlayQueryRepository extends
		PagingAndSortingRepository<PlayEntry, String> {

}
