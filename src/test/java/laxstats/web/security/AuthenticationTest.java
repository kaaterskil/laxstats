package laxstats.web.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import laxstats.TestUtils;
import laxstats.web.AbstractIntegrationTest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AuthenticationTest extends AbstractIntegrationTest {
   private static Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);
   private static String PACKAGE = "SecurityIntegrationTest.";

   @Before
   public void setup() {
      mockMvc =
         MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(
            springSecurityFilterChain).build();
   }

   @Test
   public void loginWithIncorrectCredentials() throws Exception {
      final String username = "Moe";
      final String password = "stooge";

      final Credentials credentials = new Credentials();
      credentials.setUsername(username);
      credentials.setPassword(password);

      mockMvc.perform(
         post("/api/login").contentType(contentType).content(
            TestUtils.convertObjectToJson(credentials))).andExpect(status().isUnauthorized());
   }

   /**
    * Tests for authentication with an invalid method. According to Spring Security docs, a GET
    * request renders the login page, as authentication is performed in response to a POST.
    *
    * @throws Exception
    */
   @Test
   public void loginWithIncorrectMethod() throws Exception {
      final String username = System.getenv("authentication.username");
      final String password = System.getenv("authentication.password");

      final Credentials credentials = new Credentials();
      credentials.setUsername(username);
      credentials.setPassword(password);

      mockMvc.perform(
         get("/api/login").contentType(contentType).content(
            TestUtils.convertObjectToJson(credentials))).andExpect(status().isOk());
   }

   @Test
   public void loginWithCorrectCredentials() throws Exception {
      final String proc = PACKAGE + "loginWithCorrectCredentials.";
      final String username = System.getenv("authentication.username");
      final String password = System.getenv("authentication.password");

      logger.info(proc + "Entering");
      logger.info("username: " + username);
      logger.info("password: " + password);

      final Credentials credentials = new Credentials();
      credentials.setUsername(username);
      credentials.setPassword(password);

      mockMvc.perform(
         post("/api/login").contentType(contentType).content(
            TestUtils.convertObjectToJson(credentials))).andExpect(status().isOk());

      logger.info(proc + "Leaving");
   }
}
