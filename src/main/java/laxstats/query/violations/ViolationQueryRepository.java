package laxstats.query.violations;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "penalties", path = "penalties")
public interface ViolationQueryRepository extends
		PagingAndSortingRepository<ViolationEntry, String> {

}
