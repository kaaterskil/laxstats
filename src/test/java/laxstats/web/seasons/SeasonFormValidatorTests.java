package laxstats.web.seasons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import laxstats.TestUtils;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;

import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

@RunWith(MockitoJUnitRunner.class)
public class SeasonFormValidatorTests {

   @Mock
   SeasonQueryRepository seasonQueryRepository;

   @InjectMocks
   private final SeasonFormValidator validator = new SeasonFormValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(SeasonForm.class));
      assertTrue(validator.supports(SeasonInfo.class));
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
   public void newRESTSeasonIsValid() {
      final SeasonInfo dto = new SeasonInfo();
      dto.setDescription("2003-2004 Season");
      dto.setStartsOn("2003-07-01");
      dto.setEndsOn("2004-06-30");

      final BindException errors = new BindException(dto, "seasonInfo");
      ValidationUtils.invokeValidator(validator, dto, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSeasonMissingDescription() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setDescription(null);

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonMissingStartDate() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setStartsOn(null);

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonMissingEndDate() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setEndsOn(null);

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSeasonInvalidEndDate() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setEndsOn(LocalDate.parse("2014-07-01"));

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonInvalidDates() {
      final List<SeasonEntry> list = new ArrayList<SeasonEntry>();
      list.add(TestUtils.getExistingSeason());
      Mockito.when(seasonQueryRepository.findAll()).thenReturn(list);

      final SeasonForm form = TestUtils.newSeasonForm();
      form.setStartsOn(LocalDate.parse("2014-06-01"));

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Season Tests ----------*/

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
   public void existingSeasonInvalidEndDate() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();

      final SeasonForm form = TestUtils.newSeasonForm();
      form.setEndsOn(LocalDate.parse("2014-07-01"));
      form.setId(id);

      Mockito.when(seasonQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(seasonQueryRepository.updateDescription(form.getDescription(), id)).thenReturn(0);
      Mockito.when(seasonQueryRepository.findOne(id)).thenReturn(TestUtils.getSeason());

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
