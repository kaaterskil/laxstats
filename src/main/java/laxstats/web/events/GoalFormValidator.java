package laxstats.web.events;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GoalFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return GoalForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "teamSeasonId",
				"play.teamSeasonId.required");
		ValidationUtils.rejectIfEmpty(errors, "scorerId",
				"goal.scorerId.required");
		ValidationUtils.rejectIfEmpty(errors, "elapsedTime",
				"goal.elapsedTime.required");

		final GoalForm form = (GoalForm) target;
		if (form.getPeriod() <= 0) {
			errors.rejectValue("period", "play.period.invalid");
		}
	}

}
