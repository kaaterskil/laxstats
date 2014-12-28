package laxstats.query.teamSeasons;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamSeasons", path = "teamSeasons")
public interface TeamSeasonQueryRepository extends
		PagingAndSortingRepository<TeamSeasonEntry, String> {

}
