package laxstats.web.seasons;

import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class SeasonFormValidator implements Validator {
	private SeasonQueryRepository seasonRepository;

	@Autowired
	public void setSeasonRepository(SeasonQueryRepository seasonRepository) {
		this.seasonRepository = seasonRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SeasonForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"season.description.required");
		ValidationUtils.rejectIfEmpty(errors, "startsOn",
				"season.startsOn.required");

		final SeasonForm form = (SeasonForm) target;

		// Set end date if null
		if (form.getEndsOn() == null) {
			form.setEndsOn(new LocalDate(Long.MAX_VALUE));
		}

		// Test description length
		if (form.getDescription().length() < 3) {
			errors.rejectValue("description", "season.description.tooShort");
		}

		// Test if end date is before start date
		if (form.getEndsOn().isBefore(form.getStartsOn())) {
			errors.rejectValue("endsOn", "season.endsOn.beforeStart");
		}

		// Test for date overlap with other seasons
		final Iterable<SeasonEntry> list = seasonRepository.findAll(new Sort(
				"startsOn"));
		for (final SeasonEntry each : list) {
			if (each.overlaps(form.getStartsOn(), form.getEndsOn())) {
				errors.reject("season.overlap");
			}
		}
	}
}
