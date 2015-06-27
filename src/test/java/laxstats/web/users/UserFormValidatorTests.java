package laxstats.web.users;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.users.UserQueryRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserFormValidatorTests {

   @Mock
   UserQueryRepository userQueryRepository;

   @InjectMocks
   UserFormValidator validator = new UserFormValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(UserForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void userMissingEmail() {
      final UserForm form = TestUtils.newUserForm();
      form.setEmail("   ");

      final BindException errors = new BindException(form, "userForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void userMissingLastName() {
      final UserForm form = TestUtils.newUserForm();
      form.setLastName("");

      final BindException errors = new BindException(form, "userForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New User Validation Tests ----------*/

   @Test
   public void newUserIsValid() {
      final UserForm form = TestUtils.newUserForm();

      final BindException errors = new BindException(form, "userForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newUserEmailIsDuplicate() {
      final UserForm form = TestUtils.newUserForm();

      Mockito.when(userQueryRepository.uniqueEmail(form.getEmail())).thenReturn(1);

      final BindException errors = new BindException(form, "userForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newUserEmailIsInvalid() {
      final UserForm form = TestUtils.newUserForm();
      form.setEmail("john@john@example.com");

      Mockito.when(userQueryRepository.uniqueEmail(form.getEmail())).thenReturn(0);

      final BindException errors = new BindException(form, "userForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
