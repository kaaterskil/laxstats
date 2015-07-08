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
public class UserValidatorTests {

   @Mock
   UserQueryRepository userQueryRepository;

   @InjectMocks
   UserValidator validator = new UserValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(UserResourceImpl.class));
      assertTrue(validator.supports(UserForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void userMissingEmail() {
      final UserForm form = TestUtils.newUserForm();
      form.setEmail("   ");

      final UserResourceImpl resource = TestUtils.newUserResource();
      resource.setEmail("   ");

      BindException errors = new BindException(form, "userForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "userResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void userMissingLastName() {
      final UserForm form = TestUtils.newUserForm();
      form.setLastName("");

      final UserResourceImpl resource = TestUtils.newUserResource();
      resource.setLastName("");

      BindException errors = new BindException(form, "userForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "userResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New User Validation Tests ----------*/

   @Test
   public void newUserIsValid() {
      final UserForm form = TestUtils.newUserForm();
      final UserResourceImpl resource = TestUtils.newUserResource();

      BindException errors = new BindException(form, "userForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());

      errors = new BindException(resource, "userResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
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
   public void newUserResourceEmailIsDuplicate() {
      final UserResourceImpl resource = TestUtils.newUserResource();

      Mockito.when(userQueryRepository.uniqueEmail(resource.getEmail())).thenReturn(1);

      final BindException errors = new BindException(resource, "userResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
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

   @Test
   public void newUserResourceEmailIsInvalid() {
      final UserResourceImpl resource = TestUtils.newUserResource();
      resource.setEmail("john@john@example.com");

      Mockito.when(userQueryRepository.uniqueEmail(resource.getEmail())).thenReturn(0);

      final BindException errors = new BindException(resource, "userResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }
}
