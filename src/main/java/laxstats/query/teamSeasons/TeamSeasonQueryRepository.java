package laxstats.query.teamSeasons;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamSeasons", path = "teamSeasons")
public interface TeamSeasonQueryRepository extends
		PagingAndSortingRepository<TeamSeasonEntry, String> {

	List<TeamSeasonEntry> findBySeasonId(String seasonId, Sort sort);
}
