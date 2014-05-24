package progen.context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnknownPropertyExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  
  @Test
  public void testString() {
    exception.expect(UnknownPropertyException.class);
    exception.expectMessage("Property not defined correctly.[extra]");
    throw new UnknownPropertyException("extra");
  }

}
