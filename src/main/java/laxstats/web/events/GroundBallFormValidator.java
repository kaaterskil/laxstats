package laxstats.web.events;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GroundBallFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return GroundBallForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "teamSeasonId",
				"play.teamSeasonId.required");
		ValidationUtils.rejectIfEmpty(errors, "playerId",
				"groundBall.playerId.required");

		final GroundBallForm form = (GroundBallForm) target;
		if (form.getPeriod() <= 0) {
			errors.rejectValue("period", "play.period.invalid");
		}
	}

}
