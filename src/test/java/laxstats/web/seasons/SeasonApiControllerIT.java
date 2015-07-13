package laxstats.web.seasons;

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
import laxstats.api.seasons.SeasonId;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SeasonApiControllerIT extends AbstractIntegrationTest {
   private static String BASE_REQUEST_URL = BASE_URL + "/api/seasons/";

   @Autowired
   private SeasonQueryRepository repository;

   private final List<SeasonEntry> seasons = new ArrayList<>();

   @Before
   public void setup() {
      createSecureContext();

      repository.deleteAll();

      SeasonEntry entity = new SeasonEntry();
      entity.setId(new SeasonId().toString());
      entity.setDescription("2012-2013 Season");
      entity.setStartsOn(LocalDate.parse("2012-07-01"));
      entity.setEndsOn(LocalDate.parse("2013-06-30"));
      entity.setCreatedAt(LocalDateTime.now());
      entity.setModifiedAt(LocalDateTime.now());
      repository.save(entity);
      seasons.add(entity);

      entity = new SeasonEntry();
      entity.setId(new SeasonId().toString());
      entity.setDescription("2013-2014 Season");
      entity.setStartsOn(LocalDate.parse("2013-07-01"));
      entity.setEndsOn(LocalDate.parse("2014-06-30"));
      entity.setCreatedAt(LocalDateTime.now());
      entity.setModifiedAt(LocalDateTime.now());
      repository.save(entity);
      seasons.add(entity);
   }

   /*--------- Public method tests ----------*/

   @Test
   public void getSeasons() throws Exception {
      mockMvc.perform(get(BASE_REQUEST_URL))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$", hasSize(2)));
   }

   @Test
   public void getSeasonNotFound() throws Exception {
      final String id = new SeasonId().toString();
      final String getUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isNotFound());
   }

   @Test
   public void getSeason() throws Exception {
      final SeasonEntry entity = seasons.get(0);
      final String getUrl = BASE_REQUEST_URL + entity.getId();

      mockMvc.perform(get(getUrl))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(entity.getId())));
   }

   /*---------- Admin method tests ----------*/

   @Test
   public void createSeason() throws Exception {
      final SeasonResource resource = TestUtils.newSeasonResource();

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.description", is(resource.getDescription())));
   }

   @Test
   public void createSeasonWithValidationError() throws Exception {
      final SeasonResourceImpl resource = new SeasonResourceImpl();
      resource.setDescription("2014-2015 Season");
      resource.setStartsOn("2013-07-01");
      resource.setEndsOn("2014-06-30");

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void updateSeason() throws Exception {
      final SeasonEntry entity = seasons.get(0);
      final String description = entity.getDescription() + " foo";

      final SeasonResource resource =
         new SeasonResourceImpl(entity.getId(), description, entity.getStartsOn()
            .toString(), entity.getEndsOn()
            .toString());

      mockMvc.perform(put(BASE_REQUEST_URL + entity.getId()).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.description", is(description)));
   }

   @Test
   public void updateSeasonWithValidationError() throws Exception {
      final SeasonEntry entity = seasons.get(0);

      final SeasonResource resource =
         new SeasonResourceImpl(entity.getId(), entity.getDescription(), entity.getStartsOn()
            .toString(), entity.getEndsOn()
            .toString());
      resource.setEndsOn(resource.getStartsOnAsLocalDate()
         .minusDays(5)
         .toString());

      mockMvc.perform(put(BASE_REQUEST_URL + entity.getId()).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void deleteSeason() throws Exception {
      final SeasonEntry entity = seasons.get(0);
      final String id = entity.getId();

      mockMvc.perform(delete(BASE_REQUEST_URL + id).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser()))
         .andExpect(status().isOk());
      ;
   }

   @Test
   public void deleteSeasonWithInvalidId() throws Exception {
      final String id = new SeasonId().toString();

      mockMvc.perform(delete(BASE_REQUEST_URL + id).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser()))
         .andExpect(status().isNotFound());
      ;
   }
}
