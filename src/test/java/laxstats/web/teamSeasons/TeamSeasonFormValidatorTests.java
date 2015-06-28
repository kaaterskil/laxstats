package laxstats.web.teamSeasons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

@RunWith(MockitoJUnitRunner.class)
public class TeamSeasonFormValidatorTests {

   @Mock
   TeamSeasonQueryRepository teamSeasonQueryRepository;
   @Mock
   SeasonQueryRepository seasonQueryRepository;

   @InjectMocks
   TeamSeasonFormValidator validator = new TeamSeasonFormValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(TeamSeasonForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void teamSeasonMissingTeam() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setTeam(null);

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamSeasonMissingSeason() {
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setSeason(null);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamSeasonMissingStatus() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setStatus(null);

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);
      Mockito.when(teamSeasonQueryRepository.uniqueTeamSeason(form.getTeam(), form.getSeason()))
         .thenReturn(0);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Team Season Validator Tests ----------*/

   @Test
   public void newTeamSeasonIsValid() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonIsDuplicate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);
      Mockito.when(teamSeasonQueryRepository.uniqueTeamSeason(form.getTeam(), form.getSeason()))
      .thenReturn(1);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonEarlyStartDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setStartsOn(season.getStartsOn().minusDays(10));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonLateStartDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setStartsOn(season.getStartsOn().plusYears(1));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonEarlyEndDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setEndsOn(season.getStartsOn().minusDays(10));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonLateEndDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setEndsOn(season.getEndsOn().plusDays(10));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonInvalidDates() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setStartsOn(season.getStartsOn().plusMonths(1));
      form.setEndsOn(form.getStartsOn().minusDays(3));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Team Season Validator Tests ----------*/

   @Test
   public void updatedTeamSeasonIsDuplicate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final SeasonEntry newSeason = TestUtils.getExistingSeason();

      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam().getId());
      form.setSeason(newSeason.getId());

      Mockito.when(teamSeasonQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId())).thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(
         teamSeason.getSeason());
      Mockito.when(
         teamSeasonQueryRepository.updateSeason(form.getTeam(), form.getSeason(), form.getId()))
         .thenReturn(1);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonEarlyStartDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam().getId());
      form.setSeason(teamSeason.getSeason().getId());
      form.setStartsOn(teamSeason.getSeason().getStartsOn().minusDays(10));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId())).thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(
         teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonLateStartDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam().getId());
      form.setSeason(teamSeason.getSeason().getId());
      form.setStartsOn(teamSeason.getSeason().getStartsOn().plusYears(1));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId())).thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(
         teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonEarlyEndDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam().getId());
      form.setSeason(teamSeason.getSeason().getId());
      form.setStartsOn(teamSeason.getSeason().getStartsOn().minusDays(10));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId())).thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(
         teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonLateEndDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam().getId());
      form.setSeason(teamSeason.getSeason().getId());
      form.setStartsOn(teamSeason.getSeason().getEndsOn().plusDays(10));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId())).thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(
         teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonInvalidDates() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam().getId());
      form.setSeason(teamSeason.getSeason().getId());
      form.setStartsOn(teamSeason.getSeason().getStartsOn().plusMonths(1));
      form.setEndsOn(form.getStartsOn().minusDays(3));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId())).thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason())).thenReturn(
         teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
