package laxstats.infrastructure;

import java.util.List;
import java.util.UUID;

import laxstats.domain.User;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

	List<User> findByEmail(@Param("email") String email);
}
