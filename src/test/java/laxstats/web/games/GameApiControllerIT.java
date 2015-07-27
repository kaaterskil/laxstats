package laxstats.web.games;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import laxstats.TestUtils;
import laxstats.api.games.GameId;
import laxstats.api.games.Status;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.TeamEvent;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.players.PlayerQueryRepository;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameApiControllerIT extends AbstractIntegrationTest {
   private static String BASE_REQUEST_URL = AbstractIntegrationTest.BASE_URL + "/api/games/";

   @Autowired
   private SeasonQueryRepository seasonRepository;
   @Autowired
   private TeamQueryRepository teamRepository;
   @Autowired
   private SiteQueryRepository siteRepository;
   @Autowired
   private GameQueryRepository gameRepository;
   @Autowired
   private PersonQueryRepository personRepository;
   @Autowired
   private PlayerQueryRepository playerRepository;

   private final List<TeamEntry> teams = new ArrayList<>();
   private final List<SiteEntry> sites = new ArrayList<>();
   private final List<GameEntry> games = new ArrayList<>();

   @Before
   public void setup() {
      createSecureContext();

      gameRepository.deleteAll();
      teamRepository.deleteAll();
      siteRepository.deleteAll();
      seasonRepository.deleteAll();

      // Set season
      final SeasonEntry season = TestUtils.getExistingSeason();
      seasonRepository.save(season);

      // Set first team and site
      final TeamEntry teamOne = TestUtils.getExistingTeam();
      final SiteEntry siteOne = teamOne.getHomeSite();
      siteRepository.save(siteOne);
      sites.add(siteOne);

      // Set first team season
      final TeamSeasonEntry teamOneSeason = createTeamSeason(teamOne, season);
      teamOne.addSeason(teamOneSeason);
      teamRepository.save(teamOne);
      teams.add(teamOne);

      // Set second team and site
      final TeamEntry teamTwo = TestUtils.getExistingTeam();
      teamTwo.setAbbreviation("LSRH");
      teamTwo.setName("Tigers");
      teamTwo.setSponsor("Lincoln Sudbury Regional High School");

      final SiteEntry siteTwo = teamTwo.getHomeSite();
      siteTwo.setName("Lincoln Sudbury Regional High School");
      siteRepository.save(siteTwo);
      sites.add(siteTwo);

      // Set second team season
      final TeamSeasonEntry teamTwoSeason = createTeamSeason(teamOne, season);
      teamTwo.addSeason(teamTwoSeason);
      teamRepository.save(teamTwo);
      teams.add(teamTwo);

      // Set game (no team assignments)
      final GameEntry game = TestUtils.getUnassignedGame();
      gameRepository.save(game);
      games.add(game);
   }

   /*---------- Public method tests ----------*/

   @Test
   public void readGameNotFound() throws Exception {
      final String id = new GameId().toString();
      final String getUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isNotFound());
   }

   @Test
   public void readGame() throws Exception {
      final GameEntry game = games.get(0);
      final String getUrl = BASE_REQUEST_URL + game.getId();

      mockMvc.perform(get(getUrl))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(game.getId())))
         .andExpect(jsonPath("$.status", is(Status.SCHEDULED.name())));
   }

   @Test
   public void readGames() throws Exception {
      final GameEntry game = games.get(0);
      final String getUrl = BASE_REQUEST_URL;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(game.getId())))
         .andExpect(jsonPath("$[0].status", is(Status.SCHEDULED.name())));
   }

   /*---------- Admin method tests ----------*/

   @Test
   public void createUnassignedGame() throws Exception {
      final GameResource resource = TestUtils.newGameResource();
      resource.setStartsAt("2014-03-21T16:00:00.000");

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.site", is(resource.getSite())))
         .andExpect(jsonPath("$.startsAt", is(resource.getStartsAt())));
   }

   @Test
   public void createInvalidGame() throws Exception {
      final GameResource resource = TestUtils.newGameResource();
      resource.setStartsAt(null);

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void createGame() throws Exception {
      final TeamEntry teamOne = teams.get(0);
      final TeamEntry teamTwo = teams.get(1);
      final SiteEntry site = teamOne.getHomeSite();

      final GameResource resource = TestUtils.newGameResource();
      resource.setStartsAt("2014-03-21T16:00:00.000");
      resource.setSite(site.getId());
      resource.setTeamOne(teamOne.getId());
      resource.setTeamOneHome(true);
      resource.setTeamTwo(teamTwo.getId());
      resource.setTeamTwoHome(false);

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.site", is(resource.getSite())))
         .andExpect(jsonPath("$.startsAt", is(resource.getStartsAt())));
   }

   @Test
   public void updateGame() throws Exception {
      final GameEntry game = games.get(0);
      final TeamEntry teamOne = teams.get(0);
      final TeamEntry teamTwo = teams.get(1);
      final SiteEntry site = teamOne.getHomeSite();

      final GameResource resource = convertGameEntryToResource(game);
      resource.setTeamOne(teamOne.getId());
      resource.setTeamTwo(teamTwo.getId());
      resource.setSite(site.getId());
      resource.setTeamOneHome(true);
      resource.setTeamTwoHome(false);
      final String putUrl = BASE_REQUEST_URL + game.getId();

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(game.getId())))
         .andExpect(jsonPath("$.teamOne", is(teamOne.getId())))
         .andExpect(jsonPath("$.teamTwo", is(teamTwo.getId())));
   }

   @Test
   public void updateInvalidGame() throws Exception {
      final GameEntry game = games.get(0);
      final TeamEntry teamOne = teams.get(0);
      final TeamEntry teamTwo = teams.get(1);
      final SiteEntry site = teamOne.getHomeSite();

      final GameResource resource = convertGameEntryToResource(game);
      resource.setStatus(null);
      resource.setTeamOne(teamOne.getId());
      resource.setTeamTwo(teamTwo.getId());
      resource.setSite(site.getId());
      resource.setTeamOneHome(true);
      resource.setTeamTwoHome(false);
      final String putUrl = BASE_REQUEST_URL + game.getId();

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void deleteGame() throws Exception {
      final String gameId = games.get(0)
         .getId();
      final String deleteUrl = BASE_REQUEST_URL + gameId;

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser()))
         .andExpect(status().isOk());
   }

   /*---------- Utilities ----------*/

   private TeamSeasonEntry createTeamSeason(TeamEntry team, SeasonEntry season) {
      final LocalDateTime now = LocalDateTime.now();
      final String id = new TeamSeasonId().toString();

      final TeamSeasonEntry entity = new TeamSeasonEntry();
      entity.setCreatedAt(now);
      entity.setEndsOn(season.getEndsOn());
      entity.setId(id);
      entity.setModifiedAt(now);
      entity.setName(team.getName());
      entity.setSeason(season);
      entity.setStartsOn(season.getStartsOn());
      entity.setStatus(TeamStatus.ACTIVE);
      entity.setTeam(team);
      return entity;
   }

   private GameResource convertGameEntryToResource(GameEntry entity) {
      final String siteId = entity.getSite() == null ? null : entity.getSite()
         .getId();
      final TeamEvent[] teams = entity.getTeams()
         .toArray(new TeamEvent[2]);
      final String teamOneId = teams[0] == null ? null : teams[0].getTeamSeason()
         .getId();
      final String teamTwoId = teams[1] == null ? null : teams[1].getTeamSeason()
         .getId();
      final boolean teamOneHome = entity.getHomeTeam() == null ? false : entity.getHomeTeam()
         .getId()
         .equals(teamOneId);
      final boolean teamTwoHome = entity.getHomeTeam() == null ? false : entity.getHomeTeam()
         .getId()
         .equals(teamTwoId);

      final GameResource resource = new GameResourceImpl();
      resource.setAlignment(entity.getAlignment());
      resource.setDescription(entity.getDescription());
      resource.setId(entity.getId());
      resource.setSchedule(entity.getSchedule());
      resource.setSite(siteId);
      resource.setStartsAt(entity.getStartsAt()
         .toString());
      resource.setStatus(entity.getStatus());
      resource.setTeamOne(teamOneId);
      resource.setTeamTwo(teamTwoId);
      resource.setTeamOneHome(teamOneHome);
      resource.setTeamTwoHome(teamTwoHome);
      resource.setWeather(entity.getConditions());
      return resource;
   }
}
