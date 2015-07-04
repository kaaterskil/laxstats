package laxstats.web.seasons;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import laxstats.Application;
import laxstats.query.seasons.SeasonQueryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SeasonApiControllerTests {
   private MockMvc mockMvc;

   @Autowired
   WebApplicationContext webApplicationContext;

   @Autowired
   private SeasonQueryRepository seasonQueryRepository;

   @Before
   public void setUp() throws Exception {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
   }

   @Test
   public void seasonIndex() throws Exception {
      mockMvc.perform(get("/admin/api/seasons")).andExpect(status().isOk()).andExpect(
         content().contentType(MediaType.APPLICATION_JSON_VALUE));
   }
}
