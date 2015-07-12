package laxstats.web.violations;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import laxstats.TestUtils;
import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;
import laxstats.api.violations.ViolationId;
import laxstats.query.violations.ViolationEntry;
import laxstats.query.violations.ViolationQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ViolationTest extends AbstractIntegrationTest {
   private static Logger logger = LoggerFactory.getLogger(ViolationTest.class);
   private static String BASE_REQUEST_URL = AbstractIntegrationTest.BASE_URL + "/api/violations/";

   @Autowired
   private ViolationQueryRepository repository;

   private final List<ViolationEntry> violationList = new ArrayList<>();

   @Before
   public void setup() throws Exception {
      createSecureContext();

      repository.deleteAll();

      final ViolationEntry violation = new ViolationEntry();
      violation.setId(new ViolationId().toString());
      violation.setName("Trip");
      violation.setCategory(PenaltyCategory.PERSONAL_FOUL);
      violation.setDescription("A tripping penalty");
      violation.setPenaltyLength(PenaltyLength.THIRTY_SECONDS);
      violation.setReleasable(true);

      repository.save(violation);
      violationList.add(violation);
   }

   @Test
   public void violationNotFound() throws Exception {
      final String id = new ViolationId().toString();
      final String url = BASE_REQUEST_URL + "/" + id;

      logger.info("Invalid id: " + id);
      mockMvc.perform(get(url))
         .andExpect(status().isNotFound());
   }

   @Test
   public void readViolation() throws Exception {
      final ViolationEntry violation = violationList.get(0);

      logger.info("Read id: " + violation.getId());
      mockMvc.perform(get(BASE_REQUEST_URL + violation.getId()))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(violation.getId())))
         .andExpect(jsonPath("$.name", is("Trip")))
         .andExpect(jsonPath("$.category", is(PenaltyCategory.PERSONAL_FOUL.name())))
         .andExpect(jsonPath("$.releasable", is(true)));
   }

   @Test
   public void readViolations() throws Exception {
      mockMvc.perform(get(BASE_REQUEST_URL))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(violationList.get(0)
            .getId())))
         .andExpect(jsonPath("$[0].name", is("Trip")));
   }

   @Test
   public void createViolation() throws Exception {
      final ViolationResource resource = TestUtils.newViolationResource();

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.name", is("Slash")));
   }

   @Test
   public void createViolationWithNoName() throws Exception {
      final ViolationResource resource = TestUtils.newViolationResource();
      resource.setName(null);

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .contentType(contentType)
         .header("X-AUTH-TOKEN", createTokenForUser())
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }
}
