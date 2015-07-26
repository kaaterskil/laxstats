package laxstats.query.people;

import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersonEntryTests {

   @Test
   public void getFullName() {
      final PersonEntry person = TestUtils.getPerson();
      final String fullName = PersonEntry.computeFullName(person);
      assertTrue(fullName.equals("Stanley Caple"));
   }
}
