package laxstats.web.seasons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.seasons.SeasonQueryRepository;

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
      assertTrue(validator.supports(SeasonForm.class));
      assertTrue(validator.supports(SeasonInfo.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void newSeasonIsValid() {
      final SeasonForm form = TestUtils.newSeasonForm();

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newRestSeasonIsValid() {
      final SeasonInfo dto = new SeasonInfo();
      dto.setDescription("2003-2004 Season");
      dto.setStartsOn("2003-07-01");
      dto.setEndsOn("2004-06-30");

      final BindException errors = new BindException(dto, "seasonInfo");
      ValidationUtils.invokeValidator(validator, dto, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSeasonWithoutDescription() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setDescription(null);

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonWithoutStartDate() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setStartsOn(null);

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSeasonWithoutEndDate() {
      final SeasonForm form = TestUtils.newSeasonForm();
      form.setEndsOn(null);

      final BindException errors = new BindException(form, "seasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }
}
