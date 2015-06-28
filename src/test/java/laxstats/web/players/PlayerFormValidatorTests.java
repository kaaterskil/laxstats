package laxstats.web.players;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.query.people.PersonEntry;
import laxstats.query.players.PlayerEntry;
import laxstats.query.players.PlayerQueryRepository;
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
public class PlayerFormValidatorTests {

   @Mock
   TeamSeasonQueryRepository teamSeasonQueryRepository;
   @Mock
   PlayerQueryRepository playerQueryRepository;

   @InjectMocks
   PlayerFormValidator validator = new PlayerFormValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(PlayerForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void playerMissingPerson() {
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final PlayerForm form = TestUtils.newPlayerForm();
      form.setTeamSeason(teamSeason.getId());

      Mockito.when(teamSeasonQueryRepository.findOne(form.getTeamSeason())).thenReturn(teamSeason);

      final BindException errors = new BindException(form, "playerForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void playerMissingTeamSeason() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      final PlayerForm form = TestUtils.newPlayerForm();
      form.setPerson(person.getId());

      final BindException errors = new BindException(form, "playerForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void playerMissingRole() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final PlayerForm form = TestUtils.newPlayerForm();
      form.setPerson(person.getId());
      form.setTeamSeason(teamSeason.getId());
      form.setRole(null);

      Mockito.when(teamSeasonQueryRepository.findOne(form.getTeamSeason())).thenReturn(teamSeason);

      final BindException errors = new BindException(form, "playerForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void playerMissingStatus() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final PlayerForm form = TestUtils.newPlayerForm();
      form.setPerson(person.getId());
      form.setTeamSeason(teamSeason.getId());
      form.setStatus(null);

      Mockito.when(teamSeasonQueryRepository.findOne(form.getTeamSeason())).thenReturn(teamSeason);

      final BindException errors = new BindException(form, "playerForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void playerMissingPosition() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final PlayerForm form = TestUtils.newPlayerForm();
      form.setPerson(person.getId());
      form.setTeamSeason(teamSeason.getId());
      form.setPosition(null);

      Mockito.when(teamSeasonQueryRepository.findOne(form.getTeamSeason())).thenReturn(teamSeason);

      final BindException errors = new BindException(form, "playerForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Player Form Validation Tests ----------*/

   @Test
   public void newPlayerIsValid() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      final PlayerForm form = TestUtils.newPlayerForm();
      form.setPerson(person.getId());
      form.setTeamSeason(teamSeason.getId());

      Mockito.when(teamSeasonQueryRepository.findOne(form.getTeamSeason())).thenReturn(teamSeason);

      final BindException errors = new BindException(form, "playerForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newPlayerAssignedToInactiveTeamSeason() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      final TeamSeasonEntry teamSeason = TestUtils.getExistingTeamSeason();
      teamSeason.setStatus(TeamStatus.INACTIVE);

      final PlayerForm form = TestUtils.newPlayerForm();
      form.setPerson(person.getId());
      form.setTeamSeason(teamSeason.getId());

      Mockito.when(teamSeasonQueryRepository.findOne(form.getTeamSeason())).thenReturn(teamSeason);

      final BindException errors = new BindException(form, "playerForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Player Form Validation Tests ----------*/

   @Test
   public void updatedPlayerAssignedToInactiveTeamSeason() {
      final PlayerEntry player = TestUtils.getExistingPlayer();

      final TeamSeasonEntry newTeamSeason = TestUtils.getExistingTeamSeason();
      newTeamSeason.setStatus(TeamStatus.INACTIVE);

      final PlayerForm form = TestUtils.newPlayerForm();
      form.setId(player.getId());
      form.setPerson(player.getPerson().getId());
      form.setTeamSeason(newTeamSeason.getId());

      Mockito.when(playerQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(playerQueryRepository.findOne(form.getId())).thenReturn(player);
      Mockito.when(teamSeasonQueryRepository.findOne(form.getTeamSeason()))
         .thenReturn(newTeamSeason);

      final BindException errors = new BindException(form, "playerForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}