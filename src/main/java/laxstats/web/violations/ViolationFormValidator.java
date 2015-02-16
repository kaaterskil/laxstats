package laxstats.web.violations;

import laxstats.query.violations.ViolationQueryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class ViolationFormValidator implements Validator {

	@Autowired
	private ViolationQueryRepository violationRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return ViolationForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"violation.name.required");
		ValidationUtils.rejectIfEmpty(errors, "category",
				"violation.category.required");

		final ViolationForm form = (ViolationForm) target;
		final String id = form.getId() == null ? "" : form.getId();

		// Test for uniqueness
		final int found = violationRepository.checkName(form.getName(), id);
		if (found > 0) {
			errors.rejectValue("name", "violation.name.duplicate");
		}
	}

}
