package laxstats.web.games;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.web.games.ClearForm;
import laxstats.web.games.ClearValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClearFormValidatorTests extends AbstractPlayFormValidatorTests {

   @InjectMocks
   ClearValidator validator = new ClearValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(ClearForm.class));
      assertFalse(validator.supports(Object.class));
   }
}
