package laxstats.web.violations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.violations.ViolationEntry;
import laxstats.query.violations.ViolationQueryRepository;

import org.axonframework.domain.IdentifierFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

@RunWith(MockitoJUnitRunner.class)
public class ViolationFormValidatorTests {

   @Mock
   ViolationQueryRepository violationQueryRepository;

   @InjectMocks
   ViolationFormValidator validator = new ViolationFormValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(ViolationForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void violationMissingName() {
      final ViolationForm form = TestUtils.newViolationForm();
      form.setName(null);

      final BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void violationMissingCategory() {
      final ViolationForm form = TestUtils.newViolationForm();
      form.setCategory(null);

      final BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Violation Validation Tests ----------*/

   @Test
   public void newViolationIsValid() {
      final ViolationForm form = TestUtils.newViolationForm();

      final BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newViolationDuplicateName() {
      final ViolationForm form = TestUtils.newViolationForm();

      Mockito.when(violationQueryRepository.uniqueName(form.getName())).thenReturn(1);

      final BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Violation Validation Tests ----------*/

   @Test
   public void updatedViolationHasDuplicateName() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();
      final ViolationEntry violation = TestUtils.getViolation();
      violation.setId(id);

      final ViolationForm form = TestUtils.newViolationForm();
      form.setName("This is a duplicate Name");
      form.setId(id);

      Mockito.when(violationQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(violationQueryRepository.findOne(id)).thenReturn(violation);
      Mockito.when(violationQueryRepository.updateName(form.getName(), form.getId())).thenReturn(1);

      final BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
