package laxstats.web.users;

import laxstats.query.users.UserQueryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class UserFormValidator implements Validator {

	@Autowired
	private UserQueryRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "email", "user.email.required");

		final UserForm form = (UserForm) target;
		final String id = form.getId() == null ? "" : form.getId();

		// Check for duplicate
		final int found = userRepository.checkEmail(form.getEmail(), id);
		if (found > 0) {
			errors.rejectValue("email", "user.email.duplicate");
		}
	}
}
