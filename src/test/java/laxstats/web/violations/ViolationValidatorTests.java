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
public class ViolationValidatorTests {

   @Mock
   ViolationQueryRepository violationQueryRepository;

   @InjectMocks
   ViolationValidator validator = new ViolationValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(ViolationResourceImpl.class));
      assertTrue(validator.supports(ViolationForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void violationMissingName() {
      final ViolationResource form = TestUtils.newViolationForm();
      form.setName(null);

      final ViolationResource resource = TestUtils.newViolationResource();
      resource.setName(null);

      BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "violationResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void violationMissingCategory() {
      final ViolationResource form = TestUtils.newViolationForm();
      form.setCategory(null);

      final ViolationResource resource = TestUtils.newViolationResource();
      resource.setCategory(null);

      BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "violationResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Violation Validation Tests ----------*/

   @Test
   public void newViolationIsValid() {
      final ViolationResource form = TestUtils.newViolationForm();
      final ViolationResource resource = TestUtils.newViolationResource();

      BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());

      errors = new BindException(resource, "violationResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newViolationDuplicateName() {
      final ViolationResource form = TestUtils.newViolationForm();

      Mockito.when(violationQueryRepository.uniqueName(form.getName())).thenReturn(1);

      final BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newViolationResourceDuplicateName() {
      final ViolationResource resource = TestUtils.newViolationResource();

      Mockito.when(violationQueryRepository.uniqueName(resource.getName())).thenReturn(1);

      final BindException errors = new BindException(resource, "violationResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Violation Validation Tests ----------*/

   @Test
   public void updatedViolationHasDuplicateName() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();
      final ViolationEntry violation = TestUtils.getViolation();
      violation.setId(id);

      final ViolationResource form = TestUtils.newViolationForm();
      form.setName("This is a duplicate Name");
      form.setId(id);

      Mockito.when(violationQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(violationQueryRepository.findOne(id)).thenReturn(violation);
      Mockito.when(violationQueryRepository.updateName(form.getName(), form.getId())).thenReturn(1);

      final BindException errors = new BindException(form, "violationForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedViolationResourceHasDuplicateName() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();
      final ViolationEntry violation = TestUtils.getViolation();
      violation.setId(id);

      final ViolationResource resource = TestUtils.newViolationResource();
      resource.setName("This is a duplicate Name");
      resource.setId(id);

      Mockito.when(violationQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(violationQueryRepository.findOne(id)).thenReturn(violation);
      Mockito.when(violationQueryRepository.updateName(resource.getName(), resource.getId()))
               .thenReturn(1);

      final BindException errors = new BindException(resource, "violationResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }
}
