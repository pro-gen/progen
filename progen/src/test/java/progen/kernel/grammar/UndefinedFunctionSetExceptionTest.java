package progen.kernel.grammar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UndefinedFunctionSetExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(UndefinedFunctionSetException.class);
    exception.expectMessage("extra");
    throw new UndefinedFunctionSetException("extra");
  }

}
