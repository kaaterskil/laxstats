package laxstats.web.events;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ShotFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ShotForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "teamSeasonId",
				"play.teamSeasonId.required");
		ValidationUtils.rejectIfEmpty(errors, "playerId",
				"shot.playerId.required");
		ValidationUtils.rejectIfEmpty(errors, "attemptType",
				"shot.attemptType.required");
		ValidationUtils.rejectIfEmpty(errors, "result", "shot.result.required");

		final ShotForm form = (ShotForm) target;
		if (form.getPeriod() <= 0) {
			errors.rejectValue("period", "play.period.invalid");
		}
	}

}
