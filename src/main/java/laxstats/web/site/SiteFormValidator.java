package laxstats.web.site;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class SiteFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SiteForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"site.name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city",
				"site.city.required");

		final SiteForm form = (SiteForm) target;

		if (form.getName().length() < 5) {
			errors.reject("site.name.tooShort");
		}

		if (form.getCity().length() < 5) {
			errors.reject("site.city.tooShort");
		}
	}

}
