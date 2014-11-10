package progen.kernel.evolution;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GenneticOperatorExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testString() {
    exception.expect(GenneticOperatorException.class);
    exception.expectMessage("Impossible to load the gennetic operator. (1234)");
    throw new GenneticOperatorException("1234");
  }
  
  @Test
  public void testStringException() {
    exception.expect(GenneticOperatorException.class);
    exception.expectMessage("Impossible to load the gennetic operator. (1234)");
    throw new GenneticOperatorException("1234", new IllegalArgumentException());
  }
}
