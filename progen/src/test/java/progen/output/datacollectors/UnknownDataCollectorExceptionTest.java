package progen.output.datacollectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnknownDataCollectorExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(UnknownDataCollectorException.class);
    exception.expectMessage("DataGenerator not found.(extra)");
    throw new UnknownDataCollectorException("extra");
  }

}
