package laxstats.web.violations;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;
import laxstats.api.violations.ViolationId;
import laxstats.query.violations.ViolationEntry;
import laxstats.query.violations.ViolationQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ViolationTest extends AbstractIntegrationTest {
   private static String GET_URL = "http://localhost:0/api/violations/iuyt-8765-kjhg";
   private static String GET_ALL_URL = "http://localhost:0/api/violations";

   @Autowired
   private ViolationQueryRepository repository;

   private final List<ViolationEntry> violationList = new ArrayList<>();

   @Before
   public void setup() throws Exception {
      mockMvc = webAppContextSetup(webApplicationContext).build();

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
      mockMvc.perform(get(GET_URL)).andExpect(status().isNotFound());
   }

   @Test
   public void readViolations() throws Exception {
      mockMvc.perform(get(GET_ALL_URL)).andExpect(status().isOk()).andExpect(
         jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(violationList.get(0).getId())))
         .andExpect(jsonPath("$[0].name", is("Trip")));
   }
}
