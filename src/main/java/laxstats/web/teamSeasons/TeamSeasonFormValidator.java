package laxstats.web.teamSeasons;

import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class TeamSeasonFormValidator implements Validator {
	private TeamSeasonQueryRepository teamRepository;
	private SeasonQueryRepository seasonRepository;

	@Autowired
	public void setTeamRepository(TeamSeasonQueryRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Autowired
	public void setSeasonRepository(SeasonQueryRepository seasonRepository) {
		this.seasonRepository = seasonRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return TeamSeasonForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "team",
				"teamSeason.team.required");
		ValidationUtils.rejectIfEmpty(errors, "season",
				"teamSeason.season.required");

		final TeamSeasonForm form = (TeamSeasonForm) target;

		// Test dates
		final SeasonEntry season = seasonRepository.findOne(form.getSeason());

		final LocalDate start = form.getStartsOn();
		if (start == null) {
			form.setStartsOn(season.getStartsOn());
		} else if (start.isBefore(season.getStartsOn())) {
			errors.reject("teamSeason.startsOn.tooEarly");
		} else if (start.isAfter(season.getEndsOn())) {
			errors.reject("teamSeason.startsOn.tooLate");
		}

		final LocalDate end = form.getEndsOn();
		if (end == null) {
			form.setEndsOn(season.getEndsOn());
		} else if (end.isBefore(season.getStartsOn())) {
			errors.reject("teamSeason.endsOn.tooEarly");
		} else if (end.isAfter(season.getEndsOn())) {
			errors.reject("teamSeason.endsOn.tooLate");
		}

		// Check for duplicate
		final int found = teamRepository.checkDuplicate(form.getTeam(),
				form.getSeason(), form.getId());
		if (found > 0) {
			errors.reject("teamSeason.duplicate");
		}
	}
}
