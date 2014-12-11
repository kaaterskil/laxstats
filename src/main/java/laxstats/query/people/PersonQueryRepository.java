package laxstats.query.people;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonQueryRepository extends
		PagingAndSortingRepository<PersonEntry, String> {

	List<PersonEntry> findByLastName(@Param("name") String name);
}
