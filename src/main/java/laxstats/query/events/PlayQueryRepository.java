package laxstats.query.events;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "plays", path = "plays")
public interface PlayQueryRepository extends
   PagingAndSortingRepository<PlayEntry, String> {

   @Query(value = "select exists (select te.id from team_events te " +
      "left join team_seasons ts on te.team_season_id = ts.id " +
      "left join games g on te.game_id = g.id " +
      "where ?1 is not null and g.id = cast( ?1 as varchar ) " +
      "and ?2 is not null and ts.id = cast( ?2 as varchar ))", nativeQuery = true)
   boolean teamSeasonExists(String gameId, String teamSeasonId);

}
