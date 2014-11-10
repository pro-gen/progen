package progen.output.outputers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnknowOutputerExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(UnknowOutputerException.class);
    exception.expectMessage("Outputer not found.(extra)");
    throw new UnknowOutputerException("extra");
  }

  @Test
  public void testStringException() {
    exception.expect(UnknowOutputerException.class);
    exception.expectMessage("Outputer not found.(extra)");
    throw new UnknowOutputerException("extra", new IllegalArgumentException());
  }


}
