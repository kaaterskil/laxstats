package laxstats.web;

import java.nio.charset.Charset;
import java.util.Arrays;

import laxstats.Application;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base class for all acceptance tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
abstract public class AbstractIntegrationTest {

   @Autowired
   protected WebApplicationContext webApplicationContext;

   protected MockMvc mockMvc;

   protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

   protected HttpMessageConverter<?> mappingJackson2HttpMessageConverter;

   @Autowired
   void setConverters(HttpMessageConverter<?>[] converters) {

      mappingJackson2HttpMessageConverter =
         Arrays.asList(converters).stream().filter(
            hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

      Assert.assertNotNull("the JSON message converter must not be null",
         mappingJackson2HttpMessageConverter);
   }
}
