package laxstats.web.teams;

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
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.api.teams.TeamId;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TeamApiControllerIT extends AbstractIntegrationTest {
   private static String BASE_REQUEST_URL = AbstractIntegrationTest.BASE_URL + "/api/teams/";

   @Autowired
   private TeamQueryRepository repository;

   private final List<TeamEntry> teams = new ArrayList<>();

   @Before
   public void setup() {
      createSecureContext();

      repository.deleteAll();

      TeamEntry entity = new TeamEntry();
      entity.setAbbreviation("LSRH");
      entity.setCreatedAt(LocalDateTime.now());
      entity.setGender(TeamGender.BOYS);
      entity.setId(new TeamId().toString());
      entity.setLetter(Letter.VARSITY);
      entity.setModifiedAt(LocalDateTime.now());
      entity.setName("Warriors");
      entity.setRegion(Region.MA);
      entity.setSponsor("Lincoln Sudbury Regional High School");

      entity = repository.save(entity);
      teams.add(entity);
   }

   /*---------- Public method tests ----------*/

   @Test
   public void teamNotFound() throws Exception {
      final String id = new TeamId().toString();
      final String url = BASE_REQUEST_URL + id;

      mockMvc.perform(get(url))
         .andExpect(status().isNotFound());
   }

   @Test
   public void readTeam() throws Exception {
      final TeamEntry entity = teams.get(0);
      final String id = entity.getId();
      final String url = BASE_REQUEST_URL + id;

      mockMvc.perform(get(url))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(id)))
         .andExpect(jsonPath("$.name", is(entity.getName())));
   }

   @Test
   public void readTeams() throws Exception {
      final TeamEntry entity = teams.get(0);
      final String id = entity.getId();

      mockMvc.perform(get(BASE_REQUEST_URL))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(id)))
         .andExpect(jsonPath("$[0].name", is(entity.getName())));
   }

   /*---------- Admin method tests ----------*/

   @Test
   public void createTeam() throws Exception {
      final TeamResource resource = TestUtils.newTeamResource();

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.sponsor", is(resource.getSponsor())));
   }

   @Test
   public void createTeamWithValidationError() throws Exception {
      final TeamResource resource = TestUtils.newTeamResource();
      resource.setName(null);

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void updateTeam() throws Exception {
      final TeamEntry entity = teams.get(0);
      final String newName = "Red Devils";
      final String putUrl = BASE_REQUEST_URL + entity.getId();

      final String homeSiteId = entity.getHomeSite() == null ? null : entity.getHomeSite()
         .getId();
      final String leagueId = entity.getLeague() == null ? null : entity.getLeague()
         .getId();

      final TeamResource resource =
         new TeamResourceImpl(entity.getId(), entity.getSponsor(), newName,
            entity.getAbbreviation(), entity.getGender(), entity.getLetter(), entity.getRegion(),
            homeSiteId, leagueId);

      mockMvc.perform(put(putUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.name", is(newName)));
   }

   @Test
   public void updateTeamWithValidationError() throws Exception {
      final TeamEntry entity = teams.get(0);
      final String newName = null;
      final String putUrl = BASE_REQUEST_URL + entity.getId();

      final String homeSiteId = entity.getHomeSite() == null ? null : entity.getHomeSite()
         .getId();
      final String leagueId = entity.getLeague() == null ? null : entity.getLeague()
         .getId();

      final TeamResource resource =
         new TeamResourceImpl(entity.getId(), entity.getSponsor(), newName,
            entity.getAbbreviation(), entity.getGender(), entity.getLetter(), entity.getRegion(),
            homeSiteId, leagueId);

      mockMvc.perform(put(putUrl).with(superadmin)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void deleteTeam() throws Exception {
      final TeamEntry entity = teams.get(0);
      final String id = entity.getId();

      mockMvc.perform(delete(BASE_REQUEST_URL + id).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser()))
         .andExpect(status().isOk());
   }
}
