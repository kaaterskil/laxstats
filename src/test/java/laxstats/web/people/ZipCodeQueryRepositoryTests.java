package laxstats.web.people;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.jsonwebtoken.lang.Assert;
import laxstats.Application;
import laxstats.query.people.ZipCode;
import laxstats.query.people.ZipCodeQueryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ZipCodeQueryRepositoryTests {

   @Autowired
   ZipCodeQueryRepository repository;

   ZipCode sudbury;

   @Before
   public void setUp() {
      sudbury = new ZipCode();
      sudbury.setZipCode("01776");
      sudbury.setCity("Sudbury");
   }

   @Test
   public void canFetchSudbury() {
      final String zipCode = sudbury.getZipCode();
      final ZipCode test = repository.findOne(zipCode);
      Assert.notNull(test, "test is null");
   }

   @Test
   public void canFetchAll() {
      final long count = repository.count();
      Assert.isTrue(count > 0);
   }
}
