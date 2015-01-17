package laxstats.query.teamSeasons;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamSeasons", path = "teamSeasons")
public interface TeamSeasonQueryRepository extends
		PagingAndSortingRepository<TeamSeasonEntry, String> {

	@Query("select tse from TeamSeasonEntry tse where tse.season = ?1 order by tse.affiliation.name tse.name")
	List<TeamSeasonEntry> findBySeasonId(String seasondId);
}
