package laxstats.web.teamSeasons;

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
import laxstats.api.seasons.SeasonId;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.api.teams.TeamId;
import laxstats.api.utils.Common;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TeamSeasonApiControllerIT extends AbstractIntegrationTest {

   @Autowired
   private TeamQueryRepository teamRepository;
   @Autowired
   private SeasonQueryRepository seasonRepository;
   @Autowired
   private TeamSeasonQueryRepository teamQueryRepository;

   private String baseUrl = AbstractIntegrationTest.BASE_URL + "/api/teams/";
   private final List<TeamEntry> teams = new ArrayList<>();
   private final List<SeasonEntry> seasons = new ArrayList<>();

   @Before
   public void setup() {
      createSecureContext();

      teamQueryRepository.deleteAll();
      teamRepository.deleteAll();
      seasonRepository.deleteAll();

      // Create parent team
      TeamEntry team = newTeam();
      team = teamRepository.save(team);
      teams.add(team);

      // Create seasons
      for (int i = 0; i < 2; i++) {
         final LocalDate startsOn = new LocalDate(2013 + i, 7, 1);
         final LocalDate endsOn = new LocalDate(2014 + i, 6, 30);
         final SeasonEntry aggregate = newSeason(startsOn, endsOn);

         seasonRepository.save(aggregate);
         seasons.add(aggregate);
      }

      // Create team season
      final SeasonEntry season = seasons.get(0);
      final TeamSeasonEntry teamSeason = newTeamSeason(team, season);
      team.addSeason(teamSeason);
      teamRepository.save(team);

      baseUrl = baseUrl + team.getId() + "/seasons/";
   }

   /*---------- Public method tests ----------*/

   @Test
   public void teamSeasonNotFound() throws Exception {
      final String id = new TeamSeasonId().toString();
      final String getUrl = baseUrl + id;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isNotFound());
   }

   @Test
   public void readTeamSeason() throws Exception {
      final TeamSeasonEntry entity = teams.get(0)
         .getSeasons()
         .get(0);
      final String id = entity.getId();
      final String getUrl = baseUrl + id;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(id)))
         .andExpect(jsonPath("$.name", is(entity.getName())));
   }

   @Test
   public void readTeamSeasons() throws Exception {
      final TeamSeasonEntry entity = teams.get(0)
         .getSeasons()
         .get(0);
      final String id = entity.getId();

      mockMvc.perform(get(baseUrl))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(id)))
         .andExpect(jsonPath("$[0].name", is(entity.getName())));
   }

   /*---------- Admin method tests ----------*/

   @Test
   public void createTeamSeason() throws Exception {
      final TeamEntry team = teams.get(0);
      final SeasonEntry season = seasons.get(1);
      final TeamSeasonResource resource = newTeamSeasonResource(team, season);

      mockMvc.perform(post(baseUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.name", is(resource.getName())));
   }

   @Test
   public void createTeamSeasonWithValidationError() throws Exception {
      final TeamEntry team = teams.get(0);
      final SeasonEntry season = seasons.get(0);
      final TeamSeasonResource resource = newTeamSeasonResource(team, season);

      mockMvc.perform(post(baseUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void updateTeamSeason() throws Exception {
      final TeamEntry team = teams.get(0);
      final TeamSeasonEntry entity = team.getSeasons()
         .get(0);
      final String leagueId = entity.getLeague() == null ? null : entity.getLeague()
         .getId();
      final String newName = "Red Devils";
      final String putUrl = baseUrl + entity.getId();

      final TeamSeasonResource resource = new TeamSeasonResourceImpl();
      resource.setEndsOnFromLocalDate(entity.getEndsOn());
      resource.setId(entity.getId());
      resource.setLeague(leagueId);
      resource.setName(newName);
      resource.setSeason(entity.getSeason()
         .getId());
      resource.setStartsOnFromLocalDate(entity.getStartsOn());
      resource.setStatus(entity.getStatus());
      resource.setTeam(entity.getTeam()
         .getId());

      mockMvc.perform(put(putUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(entity.getId())))
         .andExpect(jsonPath("$.name", is(newName)));
   }

   @Test
   public void updateTeamSeasonWithValidationError() throws Exception {
      final TeamEntry team = teams.get(0);
      final TeamSeasonEntry entity = team.getSeasons()
         .get(0);
      final String leagueId = entity.getLeague() == null ? null : entity.getLeague()
         .getId();
      final String putUrl = baseUrl + entity.getId();

      final TeamSeasonResource resource = new TeamSeasonResourceImpl();
      resource.setEndsOnFromLocalDate(entity.getEndsOn()
         .plusMonths(2));
      resource.setId(entity.getId());
      resource.setLeague(leagueId);
      resource.setName(entity.getName());
      resource.setSeason(entity.getSeason()
         .getId());
      resource.setStartsOnFromLocalDate(entity.getStartsOn());
      resource.setStatus(entity.getStatus());
      resource.setTeam(entity.getTeam()
         .getId());

      mockMvc.perform(put(putUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void deleteTeamSeason() throws Exception {
      final TeamEntry team = teams.get(0);
      final TeamSeasonEntry entity = team.getSeasons()
         .get(0);
      final String deleteUrl = baseUrl + entity.getId();

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType))
         .andExpect(status().isOk());
   }

   /*---------- Utilities ----------*/

   private TeamSeasonResource newTeamSeasonResource(TeamEntry team, SeasonEntry season) {
      final TeamSeasonResource resource = new TeamSeasonResourceImpl();
      resource.setEndsOnFromLocalDate(season.getEndsOn());
      resource.setName(team.getName());
      resource.setSeason(season.getId());
      resource.setStartsOnFromLocalDate(season.getStartsOn());
      resource.setStatus(TeamStatus.ACTIVE);
      resource.setTeam(team.getId());
      resource.setTeamTitle(team.getTitle());

      return resource;
   }

   private TeamSeasonEntry newTeamSeason(TeamEntry team, SeasonEntry season) {
      final LocalDateTime now = LocalDateTime.now();

      final TeamSeasonEntry entity = new TeamSeasonEntry();
      entity.setCreatedAt(now);
      entity.setEndsOn(season.getEndsOn());
      entity.setId(new TeamSeasonId().toString());
      entity.setModifiedAt(now);
      entity.setName(team.getName());
      entity.setSeason(season);
      entity.setStartsOn(season.getStartsOn());
      entity.setStatus(TeamStatus.ACTIVE);
      entity.setTeam(team);

      return entity;
   }

   private TeamEntry newTeam() {
      final LocalDateTime now = LocalDateTime.now();

      final TeamEntry aggregate = new TeamEntry();
      aggregate.setAbbreviation("LSRH");
      aggregate.setCreatedAt(now);
      aggregate.setGender(TeamGender.BOYS);
      aggregate.setId(new TeamId().toString());
      aggregate.setLetter(Letter.VARSITY);
      aggregate.setModifiedAt(now);
      aggregate.setName("Warriors");
      aggregate.setRegion(Region.MA);
      aggregate.setSponsor("Lincoln Sudbury Regional High School");

      return aggregate;
   }

   private SeasonEntry newSeason(LocalDate startsOn, LocalDate endsOn) {
      final LocalDateTime now = LocalDateTime.now();

      final SeasonEntry aggregate = new SeasonEntry();
      aggregate.setCreatedAt(now);
      aggregate.setDescription(buildSeasonDescription(startsOn, endsOn));
      aggregate.setEndsOn(endsOn);
      aggregate.setId(new SeasonId().toString());
      aggregate.setModifiedAt(now);
      aggregate.setStartsOn(startsOn);

      return aggregate;
   }

   private String buildSeasonDescription(LocalDate startsOn, LocalDate endsOn) {
      final int endsOnYear = endsOn != null ? endsOn.getYear() : Common.EOT.getYear();
      return Integer.toString(startsOn.getYear()) + "-" + Integer.toString(endsOnYear) + " Season";
   }
}
