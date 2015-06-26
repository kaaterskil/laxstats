package laxstats.web.seasons;

import laxstats.query.seasons.SeasonQueryRepository;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
      Assert.assertTrue(validator.supports(SeasonForm.class));
      Assert.assertTrue(validator.supports(SeasonInfo.class));
      Assert.assertFalse(validator.supports(Object.class));
   }

   @Test
   public void newSeasonIsValid() {
      final SeasonForm form = new SeasonForm();
      form.setDescription("2003-2004 Season");
      form.setStartsOn(LocalDate.parse("2003-07-01"));
      form.setEndsOn(LocalDate.parse("2004-06-30"));

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertFalse(errors.hasErrors());
   }

   @Test
   public void newRestSeasonIsValid() {
      final SeasonInfo dto = new SeasonInfo();
      dto.setDescription("2003-2004 Season");
      dto.setStartsOn("2003-07-01");
      dto.setEndsOn("2004-06-30");

      final BindException errors = new BindException(dto, "seasonInfo");
      ValidationUtils.invokeValidator(validator, dto, errors);
      Assert.assertFalse(errors.hasErrors());
   }

   @Test
   public void newSeasonWithoutDescription() {
      final SeasonForm form = new SeasonForm();
      form.setStartsOn(LocalDate.parse("2003-07-01"));
      form.setEndsOn(LocalDate.parse("2004-06-30"));

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonWithoutStartDate() {
      final SeasonForm form = new SeasonForm();
      form.setDescription("2003-2004 Season");

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonWithoutEndDate() {
      final SeasonForm form = new SeasonForm();
      form.setDescription("2003-2004 Season");
      form.setStartsOn(LocalDate.parse("2003-07-01"));

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertFalse(errors.hasErrors());
   }
}
