package progen.kernel.grammar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DuplicatedFunctionExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testString() {
    exception.expect(DuplicatedFunctionException.class);
    exception.expectMessage("Multiple function definitions in grammar G0");
    throw new DuplicatedFunctionException("G0");
  }

}
