package laxstats.query.events;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "plays", path = "plays")
public interface PlayQueryRepository extends
		PagingAndSortingRepository<PlayEntry, String> {

	@Query("select exists (select te.id from TeamEvent te "
			+ "where ?1 is not null and te.event.id = ?1 "
			+ "and ?2 is not null and te.teamSeason.id = ?2)")
	boolean teamSeasonExists(String gameId, String teamSeasonId);
}
