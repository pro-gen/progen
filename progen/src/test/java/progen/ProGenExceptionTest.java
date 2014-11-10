package progen;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ProGenExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(ProGenException.class);
    exception.expectMessage("extra");
    throw new ProGenException("extra");
  }

  @Test
  public void testStringException() {
    exception.expect(ProGenException.class);
    exception.expectMessage("extra");
    throw new ProGenException("extra", new IllegalArgumentException());
  }


}
