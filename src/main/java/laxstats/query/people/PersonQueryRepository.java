package laxstats.query.people;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonQueryRepository extends
		PagingAndSortingRepository<PersonEntry, String>,
		JpaSpecificationExecutor<PersonEntry> {

	List<PersonEntry> findByLastName(String lastName);

	@Query("select p from PersonEntry p left join p.addresses a left join p.contacts c where(p.firstName like %?1% and p.lastName like %?2%) order by p.lastName, p.firstName, a.region, a.city")
	List<PersonEntry> searchPeople(String firstName, String LastName);
}
