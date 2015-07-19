package laxstats.query.teamSeasons;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamSeasons", path = "teamSeasons")
public interface TeamSeasonQueryRepository extends
   PagingAndSortingRepository<TeamSeasonEntry, String>, JpaSpecificationExecutor<TeamSeasonEntry>
{

   List<TeamSeasonEntry> findBySeasonId(String seasonId, Sort sort);

   @Query(value = "select exists(select 1 from teams t "
      + "where ?1 is not null and t.id = cast(?1 as text))", nativeQuery = true)
   boolean teamExists(String teamId);

   @Query("select count(*) from TeamSeasonEntry tse "
      + "where tse.team.id = ?1 and tse.season.id = ?2")
   int uniqueTeamSeason(String teamId, String seasonId);

   @Query("select count(*) from TeamSeasonEntry tse "
      + "where tse.team.id = ?1 and tse.season.id = ?2 and tse.id <> ?3")
   int updateSeason(String teamId, String seasonId, String teamSeasonId);
}
