package laxstats.web.seasons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import laxstats.TestUtils;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;

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
public class SeasonValidatorTests {

   @Mock
   SeasonQueryRepository seasonQueryRepository;

   @InjectMocks
   private final SeasonValidator validator = new SeasonValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(SeasonForm.class));
      assertTrue(validator.supports(SeasonResourceImpl.class));
      assertFalse(validator.supports(Object.class));
   }

   /*---------- New Season Tests ----------*/

   @Test
   public void newSeasonIsValid() {
      final List<SeasonEntry> list = new ArrayList<SeasonEntry>();
      list.add(TestUtils.getExistingSeason());
      Mockito.when(seasonQueryRepository.findAll()).thenReturn(list);

      final SeasonForm form = TestUtils.newSeasonForm();

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSeasonResourceIsValid() {
      final SeasonResourceImpl resource = TestUtils.newSeasonResource();

      final BindException errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSeasonMissingDescription() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setDescription(null);

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setDescription(null);

      BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "seasonrResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonMissingStartDate() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setStartsOn(null);

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setStartsOn(null);

      BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonMissingEndDate() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setEndsOn(null);

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setEndsOn(null);

      BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());

      errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSeasonInvalidEndDate() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setEndsOn("2014-07-01");

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setEndsOn("2014-07-01");

      BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonResourceInvalidDates() {
      final List<SeasonEntry> list = new ArrayList<SeasonEntry>();
      list.add(TestUtils.getExistingSeason());
      Mockito.when(seasonQueryRepository.findAll()).thenReturn(list);

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setStartsOn("2014-06-01");

      final BindException errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonInvalidDates() {
      final List<SeasonEntry> list = new ArrayList<SeasonEntry>();
      list.add(TestUtils.getExistingSeason());
      Mockito.when(seasonQueryRepository.findAll()).thenReturn(list);

      final SeasonForm form = TestUtils.newSeasonForm();
      form.setStartsOn("2014-06-01");

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Season Tests ----------*/

   @Test
   public void existingSeasonResourceIsValid() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setDescription("2014-2015 Updated Season");
      resource.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(resource.getDescription(), id))
         .thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void existingSeasonIsValid() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonForm form = TestUtils.newSeasonForm();
      form.setDescription("2014-2015 Updated Season");
      form.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(form.getDescription(), id)).thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void existingSeasonResourceMissingDescription() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setDescription(null);
      resource.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(resource.getDescription(), id))
         .thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingSeasonMissingDescription() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonForm form = TestUtils.newSeasonForm();
      form.setDescription(null);
      form.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(form.getDescription(), id)).thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingSeasonResourceMissingStartingDate() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setStartsOn(null);
      resource.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(resource.getDescription(), id))
         .thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingSeasonMissingStartingDate() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonForm form = TestUtils.newSeasonForm();
      form.setStartsOn(null);
      form.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(form.getDescription(), id)).thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingSeasonResourceDuplicateDescription() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setDescription("This is a duplicate description");
      resource.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(resource.getDescription(), id))
         .thenReturn(1);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingSeasonDuplicateDescription() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonForm form = TestUtils.newSeasonForm();
      form.setDescription("This is a duplicate description");
      form.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(form.getDescription(), id)).thenReturn(1);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingSeasonResourceInvalidEndDate() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonResourceImpl resource = TestUtils.newSeasonResource();
      resource.setEndsOn("2014-07-01");
      resource.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(resource.getDescription(), id))
         .thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(resource, "seasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingSeasonInvalidEndDate() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonForm form = TestUtils.newSeasonForm();
      form.setEndsOn("2014-07-01");
      form.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(form.getDescription(), id)).thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
