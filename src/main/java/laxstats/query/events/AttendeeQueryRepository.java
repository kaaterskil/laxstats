package laxstats.query.events;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttendeeQueryRepository extends
		PagingAndSortingRepository<AttendeeEntry, String> {

	@Query("select ae from AttendeeEntry ae where ae.event.id = ?1 and ae.teamSeason.id = ?2 order by ae.role, ae.player.position, ae.status")
	List<AttendeeEntry> findByEventAndTeamSeason(String eventId,
			String teamSeasonId);
}
