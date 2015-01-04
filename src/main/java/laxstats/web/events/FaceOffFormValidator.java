package laxstats.web.events;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FaceOffFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return FaceOffForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "teamSeasonId",
				"play.teamSeasonId.required");
		ValidationUtils.rejectIfEmpty(errors, "winnerId",
				"faceoff.winnerId.required");
		ValidationUtils.rejectIfEmpty(errors, "loserId",
				"faceoff.loserId.required");

		final FaceOffForm form = (FaceOffForm) target;
		if (form.getPeriod() <= 0) {
			errors.rejectValue("period", "play.period.invalid");
		}
	}

}
