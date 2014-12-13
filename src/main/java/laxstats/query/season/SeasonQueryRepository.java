package laxstats.query.season;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SeasonQueryRepository extends PagingAndSortingRepository<SeasonEntry, String> {
}
