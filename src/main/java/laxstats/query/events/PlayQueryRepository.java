package laxstats.query.events;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "plays", path = "plays")
public interface PlayQueryRepository extends
		PagingAndSortingRepository<PlayEntry, String> {

	@Query(value = "select exists (select te.id from plays pl "
			+ "left join on games on pl.game_id = g.id "
			+ "left join team_events te on te.game_id = g.id "
			+ "where ?1 is not null and pl.game_id = ?1 "
			+ "and ?2 is not null and te.team_season_id = ?2)", nativeQuery = true)
	boolean teamSeasonExists(String gameId, String teamSeasonId);
}
