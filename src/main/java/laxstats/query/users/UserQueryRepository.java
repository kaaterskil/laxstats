package laxstats.query.users;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserQueryRepository extends PagingAndSortingRepository<UserEntry, String> {
	
	UserEntry findByEmail(String email);
	
	UserEntry findById(String id);
}
