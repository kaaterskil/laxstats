package laxstats.query.season;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "seasons", path = "seasons")
public interface SeasonQueryRepository extends PagingAndSortingRepository<Season, String> {
}
