package laxstats.query.violations;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "penalties", path = "penalties")
public interface ViolationQueryRepository extends
		CrudRepository<ViolationEntry, String> {

	Iterable<ViolationEntry> findAllByOrderByNameAsc();

	@Query("select count(*) from ViolationEntry v where v.name = ?1 and v.id != ?2")
	int checkName(String name, String id);
}
