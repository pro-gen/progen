package progen.output.plugins;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnknownPluginExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(UnknownPluginException.class);
    exception.expectMessage("Plugin not found.(extra)");
    throw new UnknownPluginException("extra");
  }

  @Test
  public void testStringException() {
    exception.expect(UnknownPluginException.class);
    exception.expectMessage("Plugin not found.(extra)");
    throw new UnknownPluginException("extra", new IllegalArgumentException());
  }


}
