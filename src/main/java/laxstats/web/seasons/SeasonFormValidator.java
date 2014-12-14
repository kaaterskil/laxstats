package laxstats.web.seasons;

import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class SeasonFormValidator implements Validator {

  SeasonQueryRepository repository;

  @Autowired
  public void setRepository(SeasonQueryRepository repository) {
    this.repository = repository;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return SeasonForm.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "season.description.required");
    ValidationUtils.rejectIfEmpty(errors, "startsOn", "season.startsOn.required");
    SeasonForm form = (SeasonForm) target;

    // Test if endsOn is before startsOn
    if (form.getEndsOn() != null) {
      if (form.getEndsOn().isBefore(form.getStartsOn())) {
        errors.rejectValue("endsOn", "season.endsOn.beforeStart");
      }
    }

    // Test for date overlaps with other seasons
    Iterable<SeasonEntry> list = repository.findAll();
    for (SeasonEntry e : list) {
      if (form.getStartsOn().isBefore(e.getEndsOn())) {
        errors.rejectValue("startsOn", "season.startsOn.tooEarly");
      }
    }
  }
}
