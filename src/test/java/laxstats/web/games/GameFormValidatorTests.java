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
public class GameFormValidatorTests {

   @Mock
   GameQueryRepository gameQueryRepository;
   @Mock
   TeamQueryRepository teamQueryRepository;

   @InjectMocks
   GameFormValidator validator = new GameFormValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(GameForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void gameMissingStartsAt() {
      final GameForm form = TestUtils.newGameForm();
      form.setTeamOneHome(true);
      form.setStartsAt(null);

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void gameMissingSchedule() {
      final GameForm form = TestUtils.newGameForm();
      form.setTeamOneHome(true);
      form.setSchedule(null);

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void gameMissingStatus() {
      final GameForm form = TestUtils.newGameForm();
      form.setTeamOneHome(true);
      form.setStatus(null);

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Game validator Tests ----------*/

   @Test
   public void newGameWithNoTeamsOrSiteIsValid() {
      final GameForm form = TestUtils.newGameForm();
      form.setTeamOneHome(true);

      final BindException errors = new BindException(form, "gameForm");
      ValidationUtils.invokeValidator(validator, form, errors);
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
