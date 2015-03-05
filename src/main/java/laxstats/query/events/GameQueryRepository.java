package laxstats.query.events;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "gameEntrys", path = "events")
public interface GameQueryRepository extends
		PagingAndSortingRepository<GameEntry, String> {

	@Query(value = "select count(*) from games g "
			+ "left join team_events te1 on te1.game_id = g.id "
			+ "left join team_events te2 on te2.game_id = g.id "
			+ "where cast(g.starts_at as text) = ?1 "
			+ "and ?2 is not null and g.site = ?2 "
			+ "and ?3 is not null and te1.id = ?3 "
			+ "and ?4 is not null and te2.id = ?4", nativeQuery = true)
	int checkUnique(String startsAt, String siteId, String teamOneId,
			String teamTwoId);

	@Query(value = "select count(*) from games g "
			+ "left join team_events te1 on te1.game_id = g.id "
			+ "left join team_events te2 on te2.game_id = g.id "
			+ "where cast(g.starts_at as text) = ?1 "
			+ "and ?2 is not null and g.site_id = ?2 "
			+ "and ?3 is not null and te1.id = ?3 "
			+ "and ?4 is not null and te2.id = ?4 and g.id <> ?5", nativeQuery = true)
	int checkUpdate(String startsAt, String siteId, String teamOneId,
			String teamTwoId, String gameId);
}
