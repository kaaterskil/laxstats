package laxstats.web.teamSeasons;

import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class TeamSeasonFormValidator implements Validator {
	private SeasonQueryRepository seasonRepository;

	@Autowired
	public void setRepository(SeasonQueryRepository seasonRepository) {
		this.seasonRepository = seasonRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return TeamSeasonForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "teamId",
				"teamSeason.teamId.required");
		ValidationUtils.rejectIfEmpty(errors, "seasonId",
				"teamSeason.seasonId.required");
		ValidationUtils.rejectIfEmpty(errors, "startsOn",
				"teamSeason.startsOn.required");
		final TeamSeasonForm form = (TeamSeasonForm) target;

		// Test if endsOn is before startsOn
		final LocalDate endsOn = form.getEndsOn();
		if (endsOn != null && endsOn.isBefore(form.getStartsOn())) {
			errors.rejectValue("endsOn", "teamSeason.endsOn.beforeStart");
		}

		// Test dates against season
		final SeasonEntry season = seasonRepository.findOne(form.getSeasonId());
		if (form.getStartsOn().isBefore(season.getStartsOn())) {
			errors.rejectValue("startsOn", "teamSeason.startsOn.tooEarly");
		}
		if (endsOn != null && endsOn.isAfter(season.getEndsOn())) {
			errors.rejectValue("endsOn", "teamSeason.endsOn.tooLate");
		}
	}
}
