package laxstats.web.games;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
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
public class GameValidatorTests {

   @Mock
   GameQueryRepository gameQueryRepository;
   @Mock
   TeamQueryRepository teamQueryRepository;

   @InjectMocks
   GameValidator validator = new GameValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(GameResourceImpl.class));
      assertTrue(validator.supports(GameForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void gameMissingStartsAt() {
      final GameForm form = TestUtils.newGameForm();
      form.setTeamOneHome(true);
      form.setStartsAtAsDateTime(null);

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOneHome(true);
      resource.setStartsAt(null);

      BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void gameMissingSchedule() {
      final GameForm form = TestUtils.newGameForm();
      form.setTeamOneHome(true);
      form.setSchedule(null);

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOneHome(true);
      resource.setSchedule(null);

      BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void gameMissingStatus() {
      final GameForm form = TestUtils.newGameForm();
      form.setTeamOneHome(true);
      form.setStatus(null);

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOneHome(true);
      resource.setStatus(null);

      BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Game validator Tests ----------*/

   @Test
   public void newGameWithNoTeamsOrSiteIsValid() {
      final GameForm form = TestUtils.newGameForm();
      form.setTeamOneHome(true);

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOneHome(true);

      BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());

      errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newGameResourceWithOneTeamAndNoSiteIsValid() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOne(teamOne.getTeam().getId());
      resource.setTeamOneHome(true);

      Mockito.when(teamQueryRepository.findOne(resource.getTeamOne())).thenReturn(teamOne.getTeam());

      final BindException errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newGameWithOneTeamAndNoSiteIsValid() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();

      final GameForm form = TestUtils.newGameForm();
      form.setTeamOne(teamOne.getTeam().getId());
      form.setTeamOneHome(true);

      Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne.getTeam());

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newGameResourceWithOneInvalidTeamAndNoSite() {
      final TeamEntry teamOne = TestUtils.getExistingTeam();

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOne(teamOne.getId());
      resource.setTeamOneHome(true);

      Mockito.when(teamQueryRepository.findOne(resource.getTeamOne())).thenReturn(teamOne);

      final BindException errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newGameWithOneInvalidTeamAndNoSite() {
      final TeamEntry teamOne = TestUtils.getExistingTeam();

      final GameForm form = TestUtils.newGameForm();
      form.setTeamOne(teamOne.getId());
      form.setTeamOneHome(true);

      Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne);

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newGameResourceWithTwoTeamsAndNoSiteIsValid() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final TeamSeasonEntry teamTwo = TestUtils.getExistingTeamSeason();

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOne(teamOne.getTeam().getId());
      resource.setTeamOneHome(true);
      resource.setTeamTwo(teamTwo.getTeam().getId());
      resource.setTeamTwoHome(false);

      Mockito.when(teamQueryRepository.findOne(resource.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(resource.getTeamTwo())).thenReturn(teamTwo.getTeam());

      final BindException errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newGameWithTwoTeamsAndNoSiteIsValid() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final TeamSeasonEntry teamTwo = TestUtils.getExistingTeamSeason();

      final GameForm form = TestUtils.newGameForm();
      form.setTeamOne(teamOne.getTeam().getId());
      form.setTeamOneHome(true);
      form.setTeamTwo(teamTwo.getTeam().getId());
      form.setTeamTwoHome(false);

      Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(form.getTeamTwo())).thenReturn(teamTwo.getTeam());

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newGameResourceWithTeamsAndSiteIsValid() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final TeamSeasonEntry teamTwo = TestUtils.getExistingTeamSeason();

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOne(teamOne.getTeam().getId());
      resource.setTeamOneHome(true);
      resource.setTeamTwo(teamTwo.getTeam().getId());
      resource.setTeamTwoHome(false);
      resource.setSite(teamOne.getTeam().getHomeSite().getId());

      Mockito.when(teamQueryRepository.findOne(resource.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(resource.getTeamTwo())).thenReturn(teamTwo.getTeam());

      final BindException errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newGameWithTeamsAndSiteIsValid() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final TeamSeasonEntry teamTwo = TestUtils.getExistingTeamSeason();

      final GameForm form = TestUtils.newGameForm();
      form.setTeamOne(teamOne.getTeam().getId());
      form.setTeamOneHome(true);
      form.setTeamTwo(teamTwo.getTeam().getId());
      form.setTeamTwoHome(false);
      form.setSite(teamOne.getTeam().getHomeSite().getId());

      Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(form.getTeamTwo())).thenReturn(teamTwo.getTeam());

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newGameResourceWithSameTeams() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOne(teamOne.getTeam().getId());
      resource.setTeamOneHome(true);
      resource.setTeamTwo(teamOne.getTeam().getId());
      resource.setTeamTwoHome(false);
      resource.setSite(teamOne.getTeam().getHomeSite().getId());

      Mockito.when(teamQueryRepository.findOne(resource.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(resource.getTeamTwo())).thenReturn(teamOne.getTeam());

      final BindException errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newGameWithSameTeams() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();

      final GameForm form = TestUtils.newGameForm();
      form.setTeamOne(teamOne.getTeam().getId());
      form.setTeamOneHome(true);
      form.setTeamTwo(teamOne.getTeam().getId());
      form.setTeamTwoHome(false);
      form.setSite(teamOne.getTeam().getHomeSite().getId());

      Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(form.getTeamTwo())).thenReturn(teamOne.getTeam());

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newGameResourceWithTwoHomeTeams() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final TeamSeasonEntry teamTwo = TestUtils.getExistingTeamSeason();

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOne(teamOne.getTeam().getId());
      resource.setTeamOneHome(true);
      resource.setTeamTwo(teamTwo.getTeam().getId());
      resource.setTeamTwoHome(true);
      resource.setSite(teamOne.getTeam().getHomeSite().getId());

      Mockito.when(teamQueryRepository.findOne(resource.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(resource.getTeamTwo())).thenReturn(teamTwo.getTeam());

      final BindException errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newGameWithTwoHomeTeams() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final TeamSeasonEntry teamTwo = TestUtils.getExistingTeamSeason();

      final GameForm form = TestUtils.newGameForm();
      form.setTeamOne(teamOne.getTeam().getId());
      form.setTeamOneHome(true);
      form.setTeamTwo(teamTwo.getTeam().getId());
      form.setTeamTwoHome(true);
      form.setSite(teamOne.getTeam().getHomeSite().getId());

      Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(form.getTeamTwo())).thenReturn(teamTwo.getTeam());

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newGameResourceWithTwoAwayTeams() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final TeamSeasonEntry teamTwo = TestUtils.getExistingTeamSeason();

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setTeamOne(teamOne.getTeam().getId());
      resource.setTeamOneHome(false);
      resource.setTeamTwo(teamTwo.getTeam().getId());
      resource.setTeamTwoHome(false);
      resource.setSite(teamOne.getTeam().getHomeSite().getId());

      Mockito.when(teamQueryRepository.findOne(resource.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(resource.getTeamTwo())).thenReturn(teamTwo.getTeam());

      final BindException errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newGameWithTwoAwayTeams() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final TeamSeasonEntry teamTwo = TestUtils.getExistingTeamSeason();

      final GameForm form = TestUtils.newGameForm();
      form.setTeamOne(teamOne.getTeam().getId());
      form.setTeamOneHome(false);
      form.setTeamTwo(teamTwo.getTeam().getId());
      form.setTeamTwoHome(false);
      form.setSite(teamOne.getTeam().getHomeSite().getId());

      Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne.getTeam());
      Mockito.when(teamQueryRepository.findOne(form.getTeamTwo())).thenReturn(teamTwo.getTeam());

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Game validator Tests ----------*/

   @Test
   public void updatedGameResourceWithOneTeamAndNoSiteIsValid() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final GameEntry game = TestUtils.getUnassignedGame();

      final GameResourceImpl resource = TestUtils.newGameResource();
      resource.setId(game.getId());
      resource.setTeamOne(teamOne.getTeam().getId());
      resource.setTeamOneHome(true);

      Mockito.when(gameQueryRepository.exists(resource.getId())).thenReturn(true);
      Mockito.when(gameQueryRepository.findOne(resource.getId())).thenReturn(game);
      Mockito.when(teamQueryRepository.findOne(resource.getTeamOne())).thenReturn(teamOne.getTeam());

      final BindException errors = new BindException(resource, "gameResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void updatedGameWithOneTeamAndNoSiteIsValid() {
      final TeamSeasonEntry teamOne = TestUtils.getExistingTeamSeason();
      final GameEntry game = TestUtils.getUnassignedGame();

      final GameForm form = TestUtils.newGameForm();
      form.setId(game.getId());
      form.setTeamOne(teamOne.getTeam().getId());
      form.setTeamOneHome(true);

      Mockito.when(gameQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(gameQueryRepository.findOne(form.getId())).thenReturn(game);
      Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne.getTeam());

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void updatedGameWithOneInvalidTeamAndNoSite() {
      // final TeamEntry teamOne = TestUtils.getExistingTeam();
      // final GameEntry game = TestUtils.getUnassignedGame();
      //
      // final GameForm form = TestUtils.newGameForm();
      // form.setId(game.getId());
      // form.setTeamOne(teamOne.getId());
      // form.setTeamOneHome(true);
      //
      // Mockito.when(gameQueryRepository.exists(form.getId())).thenReturn(true);
      // Mockito.when(gameQueryRepository.findOne(form.getId())).thenReturn(game);
      // Mockito.when(teamQueryRepository.findOne(form.getTeamOne())).thenReturn(teamOne);
      //
      // final BindException errors = new BindException(form, "gameForm");
      // ValidationUtils.invokeValidator(validator, form, errors);
      // assertTrue(errors.hasErrors());
   }
}
