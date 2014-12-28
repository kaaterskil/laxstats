package laxstats.web.events;

import laxstats.api.sites.SiteAlignment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EventFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return EventForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "startsAt",
				"event.startsAt.required");
		ValidationUtils.rejectIfEmpty(errors, "schedule",
				"event.schedule.required");
		ValidationUtils
				.rejectIfEmpty(errors, "status", "event.status.required");

		final EventForm form = (EventForm) target;

		if (form.getAlignment() == SiteAlignment.HOME) {
			if (!form.isTeamOneHome() && !form.isTeamTwoHome()) {
				errors.reject("event.homeTeamRequired");
			}
		}
		if (form.isTeamOneHome() && form.isTeamTwoHome()) {
			errors.reject("event.invalidHomeTeam");
		}
		if (isSameTeam(form.getTeamOneId(), form.getTeamTwoId())) {
			errors.reject("event.sameTeam");
		}
	}

	private boolean isSameTeam(String teamOneId, String teamTwoId) {
		if (teamOneId != null && teamTwoId != null) {
			return teamOneId.equals(teamTwoId);
		}
		return false;
	}

}
