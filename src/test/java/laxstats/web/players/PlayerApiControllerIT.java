package laxstats.web.players;

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
import laxstats.api.Region;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.people.PersonId;
import laxstats.api.players.PlayerId;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;
import laxstats.api.seasons.SeasonId;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.api.teams.TeamId;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.players.PlayerEntry;
import laxstats.query.players.PlayerQueryRepository;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PlayerApiControllerIT extends AbstractIntegrationTest {
   private static String BASE_REQUEST_URL = AbstractIntegrationTest.BASE_URL + "/api/teamSeasons/";

   @Autowired
   private SeasonQueryRepository seasonRepository;
   @Autowired
   private TeamQueryRepository teamRepository;
   @Autowired
   private PersonQueryRepository personRepository;
   @Autowired
   private PlayerQueryRepository playerRepository;

   List<SeasonEntry> seasons = new ArrayList<>();
   List<TeamSeasonEntry> teamSeasons = new ArrayList<>();
   List<PersonEntry> people = new ArrayList<>();

   @Before
   public void setup() {
      createSecureContext();

      teamRepository.deleteAll();
      personRepository.deleteAll();
      seasonRepository.deleteAll();

      final PersonEntry person = getPersonEntry();
      personRepository.save(person);
      people.add(person);

      final SeasonEntry season = getSeasonEntry();
      seasonRepository.save(season);
      seasons.add(season);

      final TeamEntry team = getTeamEntry();
      final TeamSeasonEntry teamSeason = getTeamSeasonEntry(team, season);

      final PlayerEntry player = getPlayerEntry(teamSeason, person);
      teamSeason.addPlayerToRoster(player);
      team.addSeason(teamSeason);
      teamRepository.save(team);

      teamSeasons.add(teamSeason);
   }

   /*---------- Public method tests ----------*/

   @Test
   public void playerNotFound() throws Exception {
      final String playerId = new PlayerId().toString();
      final String getUrl = getBaseUrl() + playerId;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isNotFound());
   }

   @Test
   public void readPlayer() throws Exception {
      final PlayerEntry player = teamSeasons.get(0)
         .getRoster()
         .get(0);
      final String getUrl = getBaseUrl() + player.getId();

      mockMvc.perform(get(getUrl))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(player.getId())))
         .andExpect(jsonPath("$.height", is(player.getHeight())))
         .andExpect(jsonPath("$.fullName", is(player.getFullName())));
   }

   @Test
   public void readPlayers() throws Exception {
      final PlayerEntry player = teamSeasons.get(0)
         .getRoster()
         .get(0);

      mockMvc.perform(get(getBaseUrl()))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(player.getId())))
         .andExpect(jsonPath("$[0].height", is(player.getHeight())))
         .andExpect(jsonPath("$[0].fullName", is(player.getFullName())));
   }

   /*---------- Admin method tests ----------*/

   @Test
   public void createPlayer() throws Exception {
      final TeamSeasonEntry teamSeason = teamSeasons.get(0);
      final PersonEntry person = getPersonEntry2();
      personRepository.save(person);

      final PlayerResource resource = getPlayerResource();
      resource.setFullName(PersonEntry.computeFullName(person));
      resource.setPerson(person.getId());
      resource.setTeamSeason(teamSeason.getId());

      mockMvc.perform(post(getBaseUrl()).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.jerseyNumber", is("14")));
   }

   @Test
   public void createInvalidPlayer() throws Exception {
      final TeamSeasonEntry teamSeason = teamSeasons.get(0);
      final PersonEntry person = getPersonEntry2();
      personRepository.save(person);

      final PlayerResource resource = getPlayerResource();
      resource.setFullName(PersonEntry.computeFullName(person));
      resource.setPerson(person.getId());
      resource.setTeamSeason(teamSeason.getId());
      resource.setPosition(null);

      mockMvc.perform(post(getBaseUrl()).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void updatePlayer() throws Exception {
      final PlayerEntry player = teamSeasons.get(0)
         .getRoster()
         .get(0);
      final String putUrl = getBaseUrl() + player.getId();

      final PlayerResource resource = convertEntryToResource(player);
      resource.setCaptain(true);

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(player.getId())))
         .andExpect(jsonPath("$.fullName", is(player.getFullName())))
         .andExpect(jsonPath("$.captain", is(true)));
   }

   @Test
   public void updateInvalidPlayer() throws Exception {
      final PlayerEntry player = teamSeasons.get(0)
         .getRoster()
         .get(0);
      final String putUrl = getBaseUrl() + player.getId();

      final PlayerResource resource = convertEntryToResource(player);
      resource.setCaptain(true);
      resource.setPosition(null);

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void dropPlayer() throws Exception {
      final PlayerEntry player = teamSeasons.get(0)
         .getRoster()
         .get(0);
      final String deleteUrl = getBaseUrl() + player.getId();

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser()))
         .andExpect(status().isOk());
   }

   /*---------- Utilities ----------*/

   private String getBaseUrl() {
      final String teamSeasonId = teamSeasons.get(0)
         .getId();
      return BASE_REQUEST_URL + teamSeasonId + "/roster/";
   }

   private PlayerResource getPlayerResource() {
      final PlayerResource resource = new PlayerResourceImpl();
      resource.setCaptain(false);
      resource.setDepth(1);
      resource.setHeight(71);
      resource.setJerseyNumber("14");
      resource.setPosition(Position.ATTACK);
      resource.setRole(Role.ATHLETE);
      resource.setStatus(PlayerStatus.ACTIVE);
      resource.setWeight(143);
      return resource;
   }

   private PlayerEntry getPlayerEntry(TeamSeasonEntry teamSeason, PersonEntry person) {
      final LocalDateTime now = LocalDateTime.now();
      final String identifier = new PlayerId().toString();

      final PlayerEntry entry = new PlayerEntry();
      entry.setCaptain(false);
      entry.setCreatedAt(now);
      entry.setDepth(1);
      entry.setFullName(PersonEntry.computeFullName(person));
      entry.setHeight(68);
      entry.setId(identifier);
      entry.setJerseyNumber("18");
      entry.setModifiedAt(now);
      entry.setPerson(person);
      entry.setPosition(Position.ATTACK);
      entry.setRole(Role.ATHLETE);
      entry.setStatus(PlayerStatus.ACTIVE);
      entry.setTeamSeason(teamSeason);
      entry.setWeight(140);
      return entry;
   }

   private PersonEntry getPersonEntry() {
      final LocalDateTime now = LocalDateTime.now();
      final String identifier = new PersonId().toString();

      final PersonEntry entry = new PersonEntry();
      entry.setBirthdate(LocalDate.parse("1986-03-05"));
      entry.setCreatedAt(now);
      entry.setDominantHand(DominantHand.RIGHT);
      entry.setFirstName("Matthew");
      entry.setGender(Gender.MALE);
      entry.setId(identifier);
      entry.setLastName("Caple");
      entry.setModifiedAt(now);
      entry.setNickname("Capes");
      return entry;
   }

   private PersonEntry getPersonEntry2() {
      final LocalDateTime now = LocalDateTime.now();
      final String identifier = new PersonId().toString();

      final PersonEntry entry = new PersonEntry();
      entry.setBirthdate(LocalDate.parse("1986-06-28"));
      entry.setCreatedAt(now);
      entry.setDominantHand(DominantHand.RIGHT);
      entry.setFirstName("Mike");
      entry.setGender(Gender.MALE);
      entry.setId(identifier);
      entry.setLastName("Stone");
      entry.setModifiedAt(now);
      return entry;
   }

   private TeamSeasonEntry getTeamSeasonEntry(TeamEntry team, SeasonEntry season) {
      final LocalDateTime now = LocalDateTime.now();
      final String identifier = new TeamSeasonId().toString();

      final TeamSeasonEntry entry = new TeamSeasonEntry();
      entry.setCreatedAt(now);
      entry.setEndsOn(season.getEndsOn());
      entry.setId(identifier);
      entry.setModifiedAt(now);
      entry.setName("Raiders");
      entry.setSeason(season);
      entry.setStartsOn(season.getStartsOn());
      entry.setStatus(TeamStatus.ACTIVE);
      entry.setTeam(team);
      return entry;
   }

   private TeamEntry getTeamEntry() {
      final LocalDateTime now = LocalDateTime.now();
      final String identifier = new TeamId().toString();

      final TeamEntry entry = new TeamEntry();
      entry.setAbbreviation("WHS");
      entry.setCreatedAt(now);
      entry.setGender(TeamGender.BOYS);
      entry.setId(identifier);
      entry.setLetter(Letter.VARSITY);
      entry.setModifiedAt(now);
      entry.setName("Raiders");
      entry.setRegion(Region.MA);
      entry.setSponsor("Wellesley High School");
      return entry;
   }

   private SeasonEntry getSeasonEntry() {
      final LocalDateTime now = LocalDateTime.now();
      final String identifier = new SeasonId().toString();

      final SeasonEntry entry = new SeasonEntry();
      entry.setCreatedAt(now);
      entry.setDescription("2003-2004 Season");
      entry.setEndsOn(LocalDate.parse("2004-06-30"));
      entry.setId(identifier);
      entry.setModifiedAt(now);
      entry.setStartsOn(LocalDate.parse("2003-07-01"));
      return entry;
   }

   private PlayerResource convertEntryToResource(PlayerEntry entity) {
      final String releaseSentOn = convertLocalDate(entity.getParentReleaseSentOn());
      final String releaseReceivedOn = convertLocalDate(entity.getParentReleaseSentOn());

      return new PlayerResourceImpl(entity.getId(), entity.getPerson()
         .getId(), entity.getTeamSeason()
         .getId(), entity.getFullName(), entity.getRole(), entity.getStatus(),
         entity.getJerseyNumber(), entity.getPosition(), entity.isCaptain(), entity.getDepth(),
         entity.getHeight(), entity.getWeight(), entity.isParentReleased(), releaseSentOn,
         releaseReceivedOn);

   }

   private String convertLocalDate(LocalDate date) {
      return date == null ? null : date.toString();
   }

}
