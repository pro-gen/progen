package progen.context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MalformedParameterExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  
  @Test
  public void testString() {
    exception.expect(MalformedParameterException.class);
    exception.expectMessage("Option malformed. (extra)");
    throw new MalformedParameterException("extra");
  }

}
