package laxstats.web.teams;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.api.Region;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

@RunWith(MockitoJUnitRunner.class)
public class TeamFormValidatorTests {

   @Mock
   TeamQueryRepository teamQueryRepository;
   @Mock
   SiteQueryRepository siteQueryRepository;

   @InjectMocks
   TeamFormValidator validator = new TeamFormValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(TeamForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void teamMissingName() {
      final TeamForm form = TestUtils.newTeamForm();
      form.setName(null);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamMissingGender() {
      final TeamForm form = TestUtils.newTeamForm();
      form.setGender(null);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamMissingLetter() {
      final TeamForm form = TestUtils.newTeamForm();
      form.setLetter(null);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamMissingRegion() {
      final TeamForm form = TestUtils.newTeamForm();
      form.setRegion(null);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Team Validation Tests ----------*/

   @Test
   public void newTeamIsValid() {
      final TeamForm form = TestUtils.newTeamForm();

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newTeamIsDuplicate() {
      final TeamForm form = TestUtils.newTeamForm();

      Mockito.when(
         teamQueryRepository.uniqueTeam(form.getSponsor(), form.getName(), form.getGender(),
            form.getLetter())).thenReturn(1);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamHasInvalidRegion() {
      final SiteEntry homeSite = TestUtils.getExistingSite();

      final TeamForm form = TestUtils.newTeamForm();
      form.setHomeSite(homeSite.getId());
      form.setRegion(Region.GA);

      Mockito.when(siteQueryRepository.findOne(form.getHomeSite())).thenReturn(homeSite);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Team Validation Tests ----------*/

   @Test
   public void updatedTeamIsDuplicate() {
      final TeamEntry team = TestUtils.getExistingTeam();
      final SiteEntry homeSite = team.getHomeSite();

      final TeamForm form = TestUtils.newTeamForm();
      form.setId(team.getId());
      form.setHomeSite(team.getHomeSite().getId());
      form.setName("Wellesley Tigers");

      Mockito.when(teamQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(teamQueryRepository.findOne(form.getId())).thenReturn(team);
      Mockito.when(
         teamQueryRepository.updateTeam(form.getSponsor(), form.getName(), form.getGender(),
            form.getLetter(), form.getId())).thenReturn(1);
      Mockito.when(siteQueryRepository.findOne(form.getHomeSite())).thenReturn(homeSite);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamHasInvalidRegion() {
      final TeamEntry team = TestUtils.getExistingTeam();
      final SiteEntry newHomeSite = TestUtils.getExistingSite();
      newHomeSite.setName("A different site");

      final TeamForm form = TestUtils.newTeamForm();
      form.setId(team.getId());
      form.setHomeSite(newHomeSite.getId());
      form.setRegion(Region.GA);

      Mockito.when(teamQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(teamQueryRepository.findOne(form.getId())).thenReturn(team);
      Mockito.when(siteQueryRepository.findOne(form.getHomeSite())).thenReturn(newHomeSite);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
