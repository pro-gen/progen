package progen.context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MalformedPercentSuboptionExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  
  @Test
  public void testString() {
    exception.expect(MalformedPercentSuboptionException.class);
    exception.expectMessage("The suboptions sumatory must result 100%. (extra)");
    throw new MalformedPercentSuboptionException("extra");
  }

}
