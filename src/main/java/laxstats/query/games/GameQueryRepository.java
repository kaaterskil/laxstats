package laxstats.query.games;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "gameEntrys", path = "events")
public interface GameQueryRepository extends PagingAndSortingRepository<GameEntry, String> {

   @Query(value = "select exists(select 1 from event_attendees ea "
      + "where ?1 is not null and ea.game_id = cast(?1 as text))", nativeQuery = true)
   boolean hasAttendees(String id);

   @Query(value = "select exists(select 1 from plays p "
      + "where ?1 is not null and p.game_id = cast(?1 as text))", nativeQuery = true)
   boolean hasPlays(String id);

   @Query(value = "select exists(select 1 from play_participants pp "
      + "inner join plays p on pp.play_id = p.id "
      + "where ?1 is not null and p.game_id = cast(?1 as text) "
      + "and ?2 is not null and pp.attendee_id = cast(?2 as text))", nativeQuery = true)
   boolean hasAttendeePlays(String gameId, String attendeeId);

   @Query("select pl from PlayEntry pl where pl.id = ?1")
   PlayEntry getPlay(String playId);

   @Query(value = "select count(*) from games g "
      + "left join team_events te1 on te1.game_id = g.id "
      + "left join team_events te2 on te2.game_id = g.id "
      + "where cast(g.starts_at as text) = ?1 and ?2 is not null and g.site_id = ?2 "
      + "and ?3 is not null and te1.id = ?3 and ?4 is not null and te2.id = ?4", nativeQuery = true)
   int checkUnique(String startsAt, String siteId, String teamOneId, String teamTwoId);

   @Query(value = "select count(*) from games g "
      + "left join team_events te1 on te1.game_id = g.id "
      + "left join team_events te2 on te2.game_id = g.id "
      + "where cast(g.starts_at as text) = ?1 and ?2 is not null and g.site_id = ?2 "
      + "and ?3 is not null and te1.id = ?3 and ?4 is not null and te2.id = ?4 and g.id <> ?5",
            nativeQuery = true)
   int
      checkUpdate(String startsAt, String siteId, String teamOneId, String teamTwoId, String gameId);
}
