package progen.kernel.evolution;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UndefinedGenneticOperatorExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(UndefinedGenneticOperatorException.class);
    exception.expectMessage("Gennetic operator not found. (extra)");
    throw new UndefinedGenneticOperatorException("extra");
  }

}
