package laxstats.web.events;

import laxstats.api.players.Role;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class AttendeeFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AttendeeForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final AttendeeForm form = (AttendeeForm) target;

		// Validate role
		ValidationUtils.rejectIfEmpty(errors, "role", "attendee.role.required");

		// Validate player
		ValidationUtils.rejectIfEmpty(errors, "playerId",
				"attendee.player.required");

		// Validate player status
		if (form.getRole().equals(Role.ATHLETE)) {
			ValidationUtils.rejectIfEmpty(errors, "status",
					"attendee.status.required");
		}
	}

}
