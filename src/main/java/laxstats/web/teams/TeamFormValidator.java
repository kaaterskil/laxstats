package laxstats.web.teams;

import laxstats.query.teams.TeamQueryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class TeamFormValidator implements Validator {

	@Autowired
	private TeamQueryRepository teamRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return TeamForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sponsor",
				"team.sponsor.required");
		ValidationUtils.rejectIfEmpty(errors, "gender", "team.gender.required");
		ValidationUtils.rejectIfEmpty(errors, "letter", "team.letter.required");
		ValidationUtils.rejectIfEmpty(errors, "region", "team.region.required");

		final TeamForm form = (TeamForm) target;

		// Test for uniqueness
		final int found = teamRepository.checkDuplicateTeam(form.getSponsor(),
				form.getName(), form.getGender(), form.getLetter());
		if (found > 0) {
			errors.reject("team.notUnique");
		}
	}

}
