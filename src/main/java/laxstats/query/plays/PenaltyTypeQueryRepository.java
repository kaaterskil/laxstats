package laxstats.query.plays;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "penalties", path = "penalties")
public interface PenaltyTypeQueryRepository extends
		PagingAndSortingRepository<PenaltyTypeEntry, String> {

}
