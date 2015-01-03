package laxstats.domain.events;

import laxstats.api.events.AttendeeDTO;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;

/**
 * Preconditions:
 * <ol>
 * <li>Attendees cannot already be registered.</li>
 * <li>Athletes must not be inactive or injured.</li>
 * </ol>
 */
public class AttendeeValidator {
	private final Event event;

	public AttendeeValidator(Event event) {
		this.event = event;
	}

	public boolean canRegisterAttendee(AttendeeDTO dto) {
		if (!isAttendeeRegistered(dto.getId())) {
			// Athlete must not be inactive or injured
			if (dto.getPlayer() != null && dto.getRole().equals(Role.ATHLETE)) {
				final PlayerStatus status = dto.getPlayer().getStatus();
				return status.equals(PlayerStatus.ACTIVE)
						|| status.equals(PlayerStatus.TRYOUT);
			}
			return true;
		}
		return false;
	}

	public boolean isAttendeeRegistered(String attendeeId) {
		return event.getAttendees().containsKey(attendeeId);
	}
}
