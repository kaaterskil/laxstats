package laxstats.query.teams;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teams", path = "teams")
public interface TeamQueryRepository extends PagingAndSortingRepository<TeamEntry, String> {

}
