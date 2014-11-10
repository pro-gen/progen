package progen.kernel.evolution.selector;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnknownSelectorExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(UnknownSelectorException.class);
    exception.expectMessage("Selector not found. (extra)");
    throw new UnknownSelectorException("extra");
  }

  @Test
  public void testStringException() {
    exception.expect(UnknownSelectorException.class);
    exception.expectMessage("Selector not found. (extra)");
    throw new UnknownSelectorException("extra", new IllegalArgumentException());
  }

}
