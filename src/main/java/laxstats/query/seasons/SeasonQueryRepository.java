package laxstats.query.seasons;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SeasonQueryRepository extends PagingAndSortingRepository<SeasonEntry, String> {

   @Query("select count(*) from SeasonEntry se where ?1 is not null "
      + "and upper(se.description) = upper(cast(?1 as text))")
   int uniqueDescription(String description);

   @Query("select count(*) from SeasonEntry se "
      + "where ?1 is not null and upper(se.description) = upper(cast(?1 as text)) "
      + "and se.id <> ?2")
   int updateDescription(String description, String id);

   @Query("select count(*) from TeamSeasonEntry tse where tse.season.id = ?1")
   int countTeamSeasons(String seasonId);

   @Query("select count(*) from GameEntry ge where ge.startsAt between ?1 and ?2")
   int countGames(LocalDateTime startsAt, LocalDateTime endsAt);
}
