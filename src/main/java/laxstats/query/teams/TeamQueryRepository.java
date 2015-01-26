package laxstats.query.teams;

import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teams", path = "teams")
public interface TeamQueryRepository extends
		PagingAndSortingRepository<TeamEntry, String> {

	@Query("select count(*) from TeamEntry t where (t.sponsor = ?1 and t.name = ?2 and t.gender = ?3 and t.letter = ?4)")
	int checkDuplicateTeam(String sponsor, String name, TeamGender gender,
			Letter letter);
}
