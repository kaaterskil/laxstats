package laxstats.query.teams;

import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TeamQueryRepository extends PagingAndSortingRepository<TeamEntry, String> {

   @Query("select count(*) from TeamEntry te "
      + "where upper(te.sponsor) = upper(cast(?1 as text)) "
      + "and upper(te.name) = upper(cast(?2 as text)) and te.gender = ?3 and te.letter = ?4")
   int uniqueTeam(String sponsor, String name, TeamGender gender, Letter letter);

   @Query("select count(*) from TeamEntry te "
      + "where te.sponsor is not null and upper(te.sponsor) = upper(cast(?1 as text)) "
      + "and te.name is not null and upper(te.name) = upper(cast(?2 as text)) "
      + "and te.gender = ?3 and te.letter = ?4 and te.id <> ?5")
   int updateTeam(String sponsor, String name, TeamGender gender, Letter letter, String id);
}
