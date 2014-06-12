package progen.kernel.evolution;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BadConfigurationGenneticOperatorsExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testDouble() {
    exception.expect(BadConfigurationGenneticOperatorsException.class);
    exception.expectMessage("The use of gennetic operators must add 100%. (30.0% != 100%)");
    throw new BadConfigurationGenneticOperatorsException(0.3);
  }

}
