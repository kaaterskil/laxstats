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
public class TeamValidatorTests {

   @Mock
   TeamQueryRepository teamQueryRepository;
   @Mock
   SiteQueryRepository siteQueryRepository;

   @InjectMocks
   TeamValidator validator = new TeamValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(TeamResourceImpl.class));
      assertTrue(validator.supports(TeamForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void teamMissingName() {
      final TeamForm form = TestUtils.newTeamForm();
      form.setName(null);

      final TeamResource resource = TestUtils.newTeamResource();
      resource.setName(null);

      BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamMissingGender() {
      final TeamForm form = TestUtils.newTeamForm();
      form.setGender(null);

      final TeamResource resource = TestUtils.newTeamResource();
      resource.setGender(null);

      BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamMissingLetter() {
      final TeamForm form = TestUtils.newTeamForm();
      form.setLetter(null);

      final TeamResource resource = TestUtils.newTeamResource();
      resource.setLetter(null);

      BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void teamMissingRegion() {
      final TeamForm form = TestUtils.newTeamForm();
      form.setRegion(null);

      final TeamResource resource = TestUtils.newTeamResource();
      resource.setRegion(null);

      BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Team Validation Tests ----------*/

   @Test
   public void newTeamResourceIsValid() {
      final TeamResource resource = TestUtils.newTeamResource();

      final BindException errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newTeamIsValid() {
      final TeamForm form = TestUtils.newTeamForm();

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newTeamResourceIsDuplicate() {
      final TeamResource resource = TestUtils.newTeamResource();

      Mockito.when(
         teamQueryRepository.uniqueTeam(resource.getSponsor(), resource.getName(),
            resource.getGender(), resource.getLetter()))
         .thenReturn(1);

      final BindException errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamIsDuplicate() {
      final TeamForm form = TestUtils.newTeamForm();

      Mockito.when(
         teamQueryRepository.uniqueTeam(form.getSponsor(), form.getName(), form.getGender(),
            form.getLetter()))
         .thenReturn(1);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamResourceHasInvalidRegion() {
      final SiteEntry homeSite = TestUtils.getExistingSite();

      final TeamResource resource = TestUtils.newTeamResource();
      resource.setHomeSite(homeSite.getId());
      resource.setRegion(Region.GA);

      Mockito.when(siteQueryRepository.findOne(resource.getHomeSite()))
         .thenReturn(homeSite);

      final BindException errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newTeamHasInvalidRegion() {
      final SiteEntry homeSite = TestUtils.getExistingSite();

      final TeamForm form = TestUtils.newTeamForm();
      form.setHomeSite(homeSite.getId());
      form.setRegion(Region.GA);

      Mockito.when(siteQueryRepository.findOne(form.getHomeSite()))
         .thenReturn(homeSite);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Team Validation Tests ----------*/

   @Test
   public void updatedTeamResourceIsDuplicate() {
      final TeamEntry team = TestUtils.getExistingTeam();
      final SiteEntry homeSite = team.getHomeSite();

      final TeamResource resource = TestUtils.newTeamResource();
      resource.setId(team.getId());
      resource.setHomeSite(team.getHomeSite()
         .getId());
      resource.setName("Wellesley Tigers");

      Mockito.when(teamQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(teamQueryRepository.findOne(resource.getId()))
         .thenReturn(team);
      Mockito.when(
         teamQueryRepository.updateTeam(resource.getSponsor(), resource.getName(),
            resource.getGender(), resource.getLetter(), resource.getId()))
         .thenReturn(1);
      Mockito.when(siteQueryRepository.findOne(resource.getHomeSite()))
         .thenReturn(homeSite);

      final BindException errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamIsDuplicate() {
      final TeamEntry team = TestUtils.getExistingTeam();
      final SiteEntry homeSite = team.getHomeSite();

      final TeamForm form = TestUtils.newTeamForm();
      form.setId(team.getId());
      form.setHomeSite(team.getHomeSite()
         .getId());
      form.setName("Wellesley Tigers");

      Mockito.when(teamQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(teamQueryRepository.findOne(form.getId()))
         .thenReturn(team);
      Mockito.when(
         teamQueryRepository.updateTeam(form.getSponsor(), form.getName(), form.getGender(),
            form.getLetter(), form.getId()))
         .thenReturn(1);
      Mockito.when(siteQueryRepository.findOne(form.getHomeSite()))
         .thenReturn(homeSite);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updatedTeamResourceHasInvalidRegion() {
      final TeamEntry team = TestUtils.getExistingTeam();
      final SiteEntry newHomeSite = TestUtils.getExistingSite();
      newHomeSite.setName("A different site");

      final TeamResource resource = TestUtils.newTeamResource();
      resource.setId(team.getId());
      resource.setHomeSite(newHomeSite.getId());
      resource.setRegion(Region.GA);

      Mockito.when(teamQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(teamQueryRepository.findOne(resource.getId()))
         .thenReturn(team);
      Mockito.when(siteQueryRepository.findOne(resource.getHomeSite()))
         .thenReturn(newHomeSite);

      final BindException errors = new BindException(resource, "teamResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
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

      Mockito.when(teamQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(teamQueryRepository.findOne(form.getId()))
         .thenReturn(team);
      Mockito.when(siteQueryRepository.findOne(form.getHomeSite()))
         .thenReturn(newHomeSite);

      final BindException errors = new BindException(form, "teamForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
