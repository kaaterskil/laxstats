package laxstats.query.players;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PlayerQueryRepository extends PagingAndSortingRepository<PlayerEntry, String> {

   @Query(value = "select exists(select 1 from event_attendees ea "
      + "where ?1 is not null and ea.player_id = cast(?1 as text))", nativeQuery = true)
   public boolean hasAttendedGames(String playerId);

   @Query(value = "select exists(select 1 from plays p "
      + "inner join team_seasons ts on p.team_season_id = ts.id "
      + "inner join players pl on pl.team_season_id = ts.id "
      + "where ?1 is not null and pl.id = cast(?1 as text))", nativeQuery = true)
   public boolean hasPlays(String playerId);
}
