package laxstats.web.events;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PenaltyFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PenaltyForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "teamSeasonId",
				"play.teamSeasonId.required");
		ValidationUtils.rejectIfEmpty(errors, "committedById",
				"penalty.committedById.required");
		ValidationUtils.rejectIfEmpty(errors, "elapsedTime",
				"penalty.elapsedTime.required");
		ValidationUtils.rejectIfEmpty(errors, "violationId",
				"penalty.violationId.required");
		ValidationUtils.rejectIfEmpty(errors, "duration",
				"penalty.duration.required");

		final PenaltyForm form = (PenaltyForm) target;
		if (form.getPeriod() <= 0) {
			errors.rejectValue("period", "play.period.invalid");
		}
		if (form.getDuration() < 0) {
			errors.rejectValue("duration", "penalty.duration.invalid");
		}
	}

}
