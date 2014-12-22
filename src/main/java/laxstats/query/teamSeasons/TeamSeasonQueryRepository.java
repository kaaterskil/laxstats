package laxstats.query.teamSeasons;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamSeasons", path = "teamSeasons")
public interface TeamSeasonQueryRepository extends
		CrudRepository<TeamSeasonEntry, String> {

}
