package laxstats.query.users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserQueryRepository extends
		PagingAndSortingRepository<UserEntry, String> {

	UserEntry findByEmail(String email);

	UserEntry findById(String id);

	@Query("select count (*) from UserEntry ue "
			+ "where upper(ue.email) = upper(?1)")
	int uniqueEmail(String email);

	@Query("select count(*) from UserEntry ue "
			+ "where ?1 is not null and upper(ue.email) = upper(?1) "
			+ "and ue.id <> ?2")
	int updateEmail(String email, String id);
}
