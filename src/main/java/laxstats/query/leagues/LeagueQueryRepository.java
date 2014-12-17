package laxstats.query.leagues;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LeagueQueryRepository extends
		PagingAndSortingRepository<LeagueEntry, String> {
}
