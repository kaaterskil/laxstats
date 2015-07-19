package laxstats.web.teamSeasons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;

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
public class TeamSeasonValidatorTests {

   @Mock
   TeamSeasonQueryRepository teamSeasonQueryRepository;
   @Mock
   SeasonQueryRepository seasonQueryRepository;

   @InjectMocks
   TeamSeasonValidator validator = new TeamSeasonValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(TeamSeasonResourceImpl.class));
      assertTrue(validator.supports(TeamSeasonForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void teamSeasonResourceMissingTeam() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setTeam(null);

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamSeasonMissingTeam() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setTeam(null);

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamSeasonResourceMissingSeason() {
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setSeason(null);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
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
   public void teamSeasonResourceMissingStatus() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setStatus(null);

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);
      Mockito.when(
         teamSeasonQueryRepository.uniqueTeamSeason(resource.getTeam(), resource.getSeason()))
         .thenReturn(0);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamSeasonMissingStatus() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setStatus(null);

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);
      Mockito.when(teamSeasonQueryRepository.uniqueTeamSeason(form.getTeam(), form.getSeason()))
         .thenReturn(0);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Team Season Validator Tests ----------*/

   @Test
   public void newTeamSeasonResourceIsValid() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);
      Mockito.when(seasonQueryRepository.exists(resource.getSeason()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.teamExists(resource.getTeam()))
         .thenReturn(true);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonIsValid() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);
      Mockito.when(teamSeasonQueryRepository.teamExists(form.getTeam()))
         .thenReturn(true);
      Mockito.when(seasonQueryRepository.exists(form.getSeason()))
         .thenReturn(true);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonResourceIsDuplicate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);
      Mockito.when(
         teamSeasonQueryRepository.uniqueTeamSeason(resource.getTeam(), resource.getSeason()))
         .thenReturn(1);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonIsDuplicate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);
      Mockito.when(teamSeasonQueryRepository.uniqueTeamSeason(form.getTeam(), form.getSeason()))
         .thenReturn(1);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonResourceEarlyStartDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setStartsOn(season.getStartsOn()
         .minusDays(10)
         .toString("yyyy-MM-dd"));

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonEarlyStartDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setStartsOn(season.getStartsOn()
         .minusDays(10));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonResourceLateStartDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setStartsOn(season.getStartsOn()
         .plusYears(1)
         .toString("yyyy-MM-dd"));

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonLateStartDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setStartsOn(season.getStartsOn()
         .plusYears(1));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonResourceEarlyEndDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setEndsOn(season.getStartsOn()
         .minusDays(10)
         .toString("yyyy-MM-dd"));

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonEarlyEndDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setEndsOn(season.getStartsOn()
         .minusDays(10));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonResourceLateEndDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setEndsOn(season.getEndsOn()
         .plusDays(10)
         .toString("yyyy-MM-dd"));

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonLateEndDate() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setEndsOn(season.getEndsOn()
         .plusDays(10));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonResourceInvalidDates() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setStartsOn(season.getStartsOn()
         .plusMonths(1)
         .toString("yyyy-MM-dd"));
      resource.setEndsOn(LocalDate.parse(resource.getStartsOn())
         .minusDays(3)
         .toString("yyyy-MM-dd"));

      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamSeasonInvalidDates() {
      final SeasonEntry season = TestUtils.getExistingSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setStartsOn(season.getStartsOn()
         .plusMonths(1));
      form.setEndsOn(form.getStartsOnAsLocalDate()
         .minusDays(3));

      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(season);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Team Season Validator Tests ----------*/

   @Test
   public void updatedTeamSeasonResourceIsDuplicate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final SeasonEntry newSeason = TestUtils.getExistingSeason();

      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setId(teamSeason.getId());
      resource.setTeam(teamSeason.getTeam()
         .getId());
      resource.setSeason(newSeason.getId());

      Mockito.when(teamSeasonQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(resource.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(teamSeason.getSeason());
      Mockito.when(
         teamSeasonQueryRepository.updateSeason(resource.getTeam(), resource.getSeason(),
            resource.getId()))
         .thenReturn(1);

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonIsDuplicate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final SeasonEntry newSeason = TestUtils.getExistingSeason();

      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam()
         .getId());
      form.setSeason(newSeason.getId());

      Mockito.when(teamSeasonQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(teamSeason.getSeason());
      Mockito.when(
         teamSeasonQueryRepository.updateSeason(form.getTeam(), form.getSeason(), form.getId()))
         .thenReturn(1);

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonResourceEarlyStartDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setId(teamSeason.getId());
      resource.setTeam(teamSeason.getTeam()
         .getId());
      resource.setSeason(teamSeason.getSeason()
         .getId());
      resource.setStartsOn(teamSeason.getSeason()
         .getStartsOn()
         .minusDays(10)
         .toString("yyyy-MM-dd"));

      Mockito.when(teamSeasonQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(resource.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonEarlyStartDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam()
         .getId());
      form.setSeason(teamSeason.getSeason()
         .getId());
      form.setStartsOn(teamSeason.getSeason()
         .getStartsOn()
         .minusDays(10));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonResourceLateStartDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setId(teamSeason.getId());
      resource.setTeam(teamSeason.getTeam()
         .getId());
      resource.setSeason(teamSeason.getSeason()
         .getId());
      resource.setStartsOn(teamSeason.getSeason()
         .getStartsOn()
         .plusYears(1)
         .toString("yyyy-MM-dd"));

      Mockito.when(teamSeasonQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(resource.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonLateStartDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam()
         .getId());
      form.setSeason(teamSeason.getSeason()
         .getId());
      form.setStartsOn(teamSeason.getSeason()
         .getStartsOn()
         .plusYears(1));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonResourceEarlyEndDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setId(teamSeason.getId());
      resource.setTeam(teamSeason.getTeam()
         .getId());
      resource.setSeason(teamSeason.getSeason()
         .getId());
      resource.setStartsOn(teamSeason.getSeason()
         .getStartsOn()
         .minusDays(10)
         .toString("yyyy-MM-dd"));

      Mockito.when(teamSeasonQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(resource.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonEarlyEndDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam()
         .getId());
      form.setSeason(teamSeason.getSeason()
         .getId());
      form.setStartsOn(teamSeason.getSeason()
         .getStartsOn()
         .minusDays(10));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonResourceLateEndDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setId(teamSeason.getId());
      resource.setTeam(teamSeason.getTeam()
         .getId());
      resource.setSeason(teamSeason.getSeason()
         .getId());
      resource.setStartsOn(teamSeason.getSeason()
         .getEndsOn()
         .plusDays(10)
         .toString("yyyy-MM-dd"));

      Mockito.when(teamSeasonQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(resource.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonLateEndDate() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam()
         .getId());
      form.setSeason(teamSeason.getSeason()
         .getId());
      form.setStartsOn(teamSeason.getSeason()
         .getEndsOn()
         .plusDays(10));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonResourceInvalidDates() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonResourceImpl resource = TestUtils.newTeamSeasonResource();
      resource.setId(teamSeason.getId());
      resource.setTeam(teamSeason.getTeam()
         .getId());
      resource.setSeason(teamSeason.getSeason()
         .getId());
      resource.setStartsOn(teamSeason.getSeason()
         .getStartsOn()
         .plusMonths(1)
         .toString("yyyy-MM-dd"));
      resource.setEndsOn(LocalDate.parse(resource.getStartsOn())
         .minusDays(3)
         .toString("yyyy-MM-dd"));

      Mockito.when(teamSeasonQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(resource.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(resource.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(resource, "teamSeasonResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamSeasonInvalidDates() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final TeamSeasonForm form = TestUtils.newTeamSeasonForm();
      form.setId(teamSeason.getId());
      form.setTeam(teamSeason.getTeam()
         .getId());
      form.setSeason(teamSeason.getSeason()
         .getId());
      form.setStartsOn(teamSeason.getSeason()
         .getStartsOn()
         .plusMonths(1));
      form.setEndsOn(form.getStartsOnAsLocalDate()
         .minusDays(3));

      Mockito.when(teamSeasonQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getId()))
         .thenReturn(teamSeason);
      Mockito.when(seasonQueryRepository.findOne(form.getSeason()))
         .thenReturn(teamSeason.getSeason());

      final BindException errors = new BindException(form, "teamSeasonForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
