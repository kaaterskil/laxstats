package laxstats.query.teamSeasons;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamSeasons", path = "teamSeasons")
public interface TeamSeasonQueryRepository extends
		PagingAndSortingRepository<TeamSeasonEntry, String>,
		JpaSpecificationExecutor<TeamSeasonEntry> {

	List<TeamSeasonEntry> findBySeasonId(String seasonId, Sort sort);

	@Query(value = "select count(*) from team_seasons ts where (ts.team_id = ?1 and ts.season_id = ?2 and ts.id != ?3)", nativeQuery = true)
	int checkDuplicate(String teamId, String seasonId, String teamSeasonId);
}
