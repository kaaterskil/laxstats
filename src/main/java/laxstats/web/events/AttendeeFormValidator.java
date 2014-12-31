package laxstats.web.events;

import laxstats.api.players.Role;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AttendeeFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AttendeeForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "role", "attendee.role.required");
		final AttendeeForm form = (AttendeeForm) target;

		// Test for invariants
		final String teamSeasonId = form.getTeamSeasonId();
		final String playerId = form.getPlayerId();
		if (teamSeasonId == null) {
			if (playerId != null) {
				errors.reject("attendee.teamRequiredForPlayer");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
					"attendee.officialNameRequired");
		} else {
			ValidationUtils.rejectIfEmpty(errors, "playerId",
					"attendee.player.required");
			if (form.getRole().equals(Role.ATHLETE)) {
				ValidationUtils.rejectIfEmpty(errors, "status",
						"attendee.status.required");
			}
		}
	}

}
