package laxstats.query.sites;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "sites", path = "sites")
public interface SiteQueryRepository extends
		PagingAndSortingRepository<SiteEntry, String> {
}
