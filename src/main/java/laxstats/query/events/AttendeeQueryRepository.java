package laxstats.query.events;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttendeeQueryRepository extends
		PagingAndSortingRepository<AttendeeEntry, String> {

	@Query("select eae from EventAttendeeEntry eae where eae.event.id = ?1 and eae.teamSeason.id = ?2 order by eae.role, eae.player.position, eae.status")
	List<AttendeeEntry> findByEventAndTeamSeason(String eventId,
			String teamSeasonId);
}
