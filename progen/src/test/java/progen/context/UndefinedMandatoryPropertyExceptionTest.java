package progen.context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UndefinedMandatoryPropertyExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testString() {
    exception.expect(UndefinedMandatoryPropertyException.class);
    exception.expectMessage("Undefined mandatory property. [extra]");
    throw new UndefinedMandatoryPropertyException("extra");
  }

}
