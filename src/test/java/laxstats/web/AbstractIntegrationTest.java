package laxstats.web;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.Charset;
import java.util.Arrays;

import laxstats.Application;
import laxstats.web.security.TokenAuthenticationService;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base class for all acceptance tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles(value = "test")
abstract public class AbstractIntegrationTest {
   protected static String AUTH_HEADER_NAME = TokenAuthenticationService.AUTH_HEADER_NAME;
   protected static String BASE_URL = "http://localhost:0";

   @Autowired
   protected WebApplicationContext webApplicationContext;

   @Autowired
   protected FilterChainProxy springSecurityFilterChain;

   @Value(value = "${app.admin.token.secret}")
   protected String secret;

   protected RequestPostProcessor superadmin;

   protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

   protected HttpMessageConverter<?> mappingJackson2HttpMessageConverter;

   protected MockMvc mockMvc;

   @Autowired
   void setConverters(HttpMessageConverter<?>[] converters) {
      mappingJackson2HttpMessageConverter =
         Arrays.asList(converters).stream().filter(
            hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

      Assert.assertNotNull("the JSON message converter must not be null",
         mappingJackson2HttpMessageConverter);
   }

   /*---------- Utility methods ----------*/

   /**
    * Creates a secure web application context and superuser. Must be called from the concrete
    * test's {@code @Before} method.
    */
   protected void createSecureContext() {
      mockMvc =
         webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();

      final String username = System.getenv("authentication.username");
      final String password = System.getenv("authentication.password");
      superadmin = CustomSecurityMockMvcRequestPostProcessors.superadmin(username, password);
   }

   /**
    * Return's the generated user's authentication token. Must be added to the header of each mock
    * request.
    *
    * @return
    */
   protected String createTokenForUser() {
      final String username = System.getenv("authentication.username");

      return Jwts
         .builder()
            .setSubject(username)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
   }
}
