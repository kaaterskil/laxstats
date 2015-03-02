package laxstats.query.violations;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "penalties", path = "penalties")
public interface ViolationQueryRepository extends
		CrudRepository<ViolationEntry, String> {

	Iterable<ViolationEntry> findAllByOrderByNameAsc();

	@Query("select count(*) from ViolationEntry ve "
			+ "where upper(ve.name) = upper(?1)")
	int uniqueName(String name);

	@Query("select count(*) from ViolationEntry ve "
			+ "where ?1 is not null and upper(ve.name) = upper(?1) "
			+ "and ve.id <> ?2")
	int updateName(String name, String id);
}
