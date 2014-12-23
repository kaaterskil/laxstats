package laxstats.query.players;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface PlayerQueryRepository extends
		PagingAndSortingRepository<PlayerEntry, String> {

}
